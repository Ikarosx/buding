package cn.budingcc.es.service.impl;

import cn.budingcc.es.dao.EsGoodRepository;
import cn.budingcc.es.domain.EsGood;
import cn.budingcc.es.service.EsShopService;
import cn.budingcc.framework.domain.shop.request.GoodSearchParam;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.QueryResult;
import cn.budingcc.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Ikaros
 * @date 2020/2/23 21:11
 */
@Service("EsShopService")
public class EsShopServiceImpl implements EsShopService {
    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;
    
    @Autowired
    EsGoodRepository esGoodRepository;
    
    @Override
    public QueryResponseResult listGoods(int page, int size, GoodSearchParam goodSearchParam) {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 设置类型
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 关键字
        String keyword = goodSearchParam.getKeyword();
        if (StringUtils.isNotEmpty(keyword)) {
            // multiMatch
            // 匹配关键字
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "good_name", "description");
            // 设置匹配占比
            multiMatchQueryBuilder.minimumShouldMatch("75%");
            // 提升特定字段的boost值
            multiMatchQueryBuilder.field("good_name", 5);
            // 模糊性
            multiMatchQueryBuilder.fuzziness(1);
            // 最大模糊扩展数
            multiMatchQueryBuilder.maxExpansions(100);
            boolQueryBuilder.should(multiMatchQueryBuilder);
            // wildcard
            boolQueryBuilder.should(QueryBuilders.wildcardQuery("good_name", "*" + keyword + "*"));
            // 解决filter和should交集问题
            boolQueryBuilder.minimumShouldMatch(1);
        }
        // 过滤
        String directCategoryId = goodSearchParam.getDirectCategoryId();
        if (StringUtils.isNotEmpty(directCategoryId)) {
            // prefixQuery会在分类超过10个的时候出现搜索bug
            boolQueryBuilder.filter(QueryBuilders.prefixQuery("direct_category_id", directCategoryId));
        }
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        // 分页
        
        nativeSearchQueryBuilder.withPageable(PageRequest.of(page - 1, size));
        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("goodName");
        nativeSearchQueryBuilder.withHighlightBuilder(highlightBuilder);
        // 排序
        String sort = goodSearchParam.getSort();
        if (StringUtils.isNotEmpty(sort)) {
            for (String s : sort.split("-")) {
                String[] split = s.split(":");
                System.out.println(split.length);
                if (split.length != 2) {
                    continue;
                }
                FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort(split[0]);
                // 0升1降
                fieldSortBuilder.order("true".equals(split[1]) ? SortOrder.DESC : SortOrder.ASC);
                nativeSearchQueryBuilder.withSort(fieldSortBuilder);
            }
        }
        AggregatedPage<EsGood> goods = elasticsearchRestTemplate.queryForPage(nativeSearchQueryBuilder.build(), EsGood.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<EsGood> chunk = new ArrayList<>();
                for (SearchHit searchHit : response.getHits()) {
                    if (response.getHits().getHits().length <= 0) {
                        return null;
                    }
                    EsGood good = new EsGood();
                    good.setId(searchHit.getId());
                    Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                    good.setPrice(Double.valueOf(sourceAsMap.get("price").toString()));
                    good.setImageUrl((String) sourceAsMap.get("image_url"));
                    good.setDirectCategoryId((String) sourceAsMap.get("direct_category_id"));
                    good.setDescription((String) sourceAsMap.get("description"));
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    Date createTime = null;
                    try {
                        String create_time = (String) sourceAsMap.get("create_time");
                        createTime = simpleDateFormat.parse(create_time);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    good.setCreateTime(createTime);
                    HighlightField highlightField = searchHit.getHighlightFields().get("good_name");
                    if (highlightField != null) {
                        good.setGoodName(highlightField.fragments()[0].toString());
                    } else {
                        good.setGoodName((String) sourceAsMap.get("good_name"));
                    }
                    chunk.add(good);
                }
                if (chunk.size() > 0) {
                    return new AggregatedPageImpl<>((List<T>) chunk);
                }
                return null;
            }
            
            @Override
            public <T> T mapSearchHit(SearchHit searchHit, Class<T> type) {
                return null;
            }
        });
        
        QueryResult<EsGood> queryResult = new QueryResult();
        
        List<EsGood> list = goods == null ? null : goods.getContent();
        long totalPages = goods == null ? 0 : goods.getTotalPages();
        queryResult.setList(list);
        queryResult.setTotalPage(totalPages);
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
    
    @Override
    public ResponseResult deleteGood(String id) {
        esGoodRepository.deleteById(id);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult deleteGoods(String ids) {
        List<EsGood> esGoodList = new LinkedList<>();
        for (String id : ids.split("-")) {
            EsGood esGood = new EsGood();
            esGood.setId(id);
            esGoodList.add(esGood);
        }
        esGoodRepository.deleteAll(esGoodList);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
}
