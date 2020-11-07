package cn.budingcc.es.service.impl;

import cn.budingcc.es.domain.EsGood;
import cn.budingcc.es.service.EsShopService;
import cn.budingcc.framework.domain.shop.request.GoodSearchParam;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.QueryResult;
import cn.budingcc.framework.model.response.ResponseResult;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ikaros
 * @date 2020/2/23 21:11
 */
@Service("EsShopService")
public class EsShopServiceImpl implements EsShopService {
    // @Autowired
    // ElasticsearchRestTemplate elasticsearchRestTemplate;
    private static final String BD_GOOD = "bd_good";
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    // @Autowired
    // EsGoodRepository esGoodRepository;
    
    @Override
    public QueryResponseResult listGoods(int page, int size, GoodSearchParam goodSearchParam) {
        SearchRequest searchRequest = new SearchRequest("bd_good");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        String keyword = goodSearchParam.getKeyword();
        if (StringUtils.isNotBlank(keyword)) {
            // multiMatch
            // 匹配关键字
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "good_name", "description");
            // 设置匹配占比
            multiMatchQueryBuilder.minimumShouldMatch("75%");
            // 提升特定字段的boost值
            multiMatchQueryBuilder.field("good_name", 5);
            // 模糊性
            // multiMatchQueryBuilder.fuzziness(1);
            // 最大模糊扩展数
            multiMatchQueryBuilder.maxExpansions(2);
            boolQueryBuilder.should(multiMatchQueryBuilder);
            // wildcard
            // boolQueryBuilder.should(QueryBuilders.wildcardQuery("good_name", "*" + keyword + "*"));
            // 解决filter和should交集问题
            // boolQueryBuilder.minimumShouldMatch(1);
        }
        // 过滤
        String directCategoryId = goodSearchParam.getDirectCategoryId();
        if (StringUtils.isNotEmpty(directCategoryId)) {
            // prefixQuery会在分类超过10个的时候出现搜索bug
            boolQueryBuilder.filter(QueryBuilders.prefixQuery("direct_category_id.keyword", directCategoryId));
        }
        searchSourceBuilder.query(boolQueryBuilder);
        
        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("good_name");
        searchSourceBuilder.highlighter(highlightBuilder);
        
        // 分页
        searchSourceBuilder.from(page - 1);
        searchSourceBuilder.size(size);
        // 排序
        String sort = goodSearchParam.getSort();
        if (StringUtils.isNotEmpty(sort)) {
            for (String s : sort.split("-")) {
                String[] split = s.split(":");
                if (split.length != 2) {
                    continue;
                }
                FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort(split[0]);
                // 0升1降
                fieldSortBuilder.order("true".equals(split[1]) ? SortOrder.DESC : SortOrder.ASC);
                searchSourceBuilder.sort(fieldSortBuilder);
            }
        }
        
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return new QueryResponseResult(CommonCodeEnum.FAIL, null);
        }
        SearchHits hits = response.getHits();
        SearchHit[] searchHits = hits.getHits();
        List<EsGood> result = new LinkedList<>();
        for (SearchHit hit : searchHits) {
            String sourceAsString = hit.getSourceAsString();
            EsGood esGood = JSON.parseObject(sourceAsString, EsGood.class);
            HighlightField highlightField = hit.getHighlightFields().get("good_name");
            if (highlightField != null) {
                Text[] fragments = highlightField.fragments();
                StringBuilder builder = new StringBuilder();
                for (Text fragment : fragments) {
                    builder.append(fragment);
                }
                esGood.setGoodName(builder.toString());
            }
            result.add(esGood);
        }
        long totalHits = hits.getTotalHits().value;
        QueryResult<EsGood> queryResult = new QueryResult<>();
        queryResult.setList(result);
        long totalPage = totalHits / size;
        if (totalHits % size != 0) {
            totalPage++;
        }
        queryResult.setTotalPage(totalPage);
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
    
    @Override
    public ResponseResult deleteGood(String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(BD_GOOD, id);
        restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult deleteGoods(String ids) throws IOException {
        for (String id : ids.split("-")) {
            deleteGood(id);
        }
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
}
