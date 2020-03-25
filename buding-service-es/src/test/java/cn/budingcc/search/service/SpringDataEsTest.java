package cn.budingcc.search.service;

import cn.budingcc.es.ESApplication;
import cn.budingcc.es.service.EsShopService;
import cn.budingcc.framework.domain.shop.Good;
import cn.budingcc.framework.domain.shop.request.GoodSearchParam;
import cn.budingcc.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ikaros
 * @date 2020/2/24 17:28
 */
@SpringBootTest(classes = ESApplication.class)
@RunWith(SpringRunner.class)
public class SpringDataEsTest {
    //    @Autowired
    //    EsGoodRepository esGoodRepository;
    
    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    EsShopService esShopService;
    
    @Test
    public void deleteTest() {
        ResponseResult responseResult = esShopService.deleteGood("123");
        System.out.println(responseResult);
    }
    
    @Test
    public void esTest() {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        String testString = "我的老婆";
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(testString, "goodName", "description");
        multiMatchQueryBuilder.minimumShouldMatch("70%");
        multiMatchQueryBuilder.field("goodName", 10);
        boolQueryBuilder.must(multiMatchQueryBuilder);
        nativeSearchQueryBuilder.withQuery(multiMatchQueryBuilder);
        nativeSearchQueryBuilder.withHighlightFields(new HighlightBuilder.Field("goodName"));
        AggregatedPage<Good> goods = elasticsearchRestTemplate.queryForPage(nativeSearchQueryBuilder.build(), Good.class, new SearchResultMapper() {
            
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                List<Good> chunk = new ArrayList<>();
                for (SearchHit searchHit : response.getHits()) {
                    if (response.getHits().getHits().length <= 0) {
                        return null;
                    }
                    Good good = new Good();
                    good.setId(searchHit.getId());
                    good.setPrice(Double.valueOf(searchHit.getSourceAsMap().get("price").toString()));
                    good.setImageUrl((String) searchHit.getSourceAsMap().get("imageUrl"));
                    good.setDescription((String) searchHit.getSourceAsMap().get("description"));
                    HighlightField highlightField = searchHit.getHighlightFields().get("goodName");
                    if (highlightField != null) {
                        good.setGoodName(highlightField.fragments()[0].toString());
                    } else {
                        good.setGoodName((String) searchHit.getSourceAsMap().get("goodName"));
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
        if (goods != null) {
            System.out.println(goods.getContent());
        } else {
            System.out.println("goods not found");
        }
        
    }
    
    @Test
    public void es2Test() {
        GoodSearchParam goodSearchParam = new GoodSearchParam();
        goodSearchParam.setKeyword("Ikaros");
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 设置类型
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // 关键字
        String keyword = goodSearchParam.getKeyword();
        if (StringUtils.isNotEmpty(keyword)) {
            // 匹配关键字
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "goodName", "description");
            // 设置匹配占比
            multiMatchQueryBuilder.minimumShouldMatch("70%");
            // 提升特定字段的boost值
            multiMatchQueryBuilder.field("goodName", 10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }
        String directCategoryId = goodSearchParam.getDirectCategoryId();
        if (StringUtils.isNotEmpty(directCategoryId)) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("directCategoryId", directCategoryId));
        }
        nativeSearchQueryBuilder.withQuery(boolQueryBuilder);
        // 分页
        int page = 1;
        int size = 10;
        nativeSearchQueryBuilder.withPageable(PageRequest.of(page - 1, size));
        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("goodName");
        nativeSearchQueryBuilder.withHighlightBuilder(highlightBuilder);
        //        Page<Good> goodPage = esGoodRepository.search(nativeSearchQueryBuilder.build());
        //        System.out.println(goodPage.getContent());
        /*// 结果集处理
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        // 记录总数
        long totalHists = hits.getTotalHits();
        // 数据列表
        List<Good> goodList = new ArrayList<>();
        for (SearchHit searchHit : searchHits) {
            Good good = new Good();
            // 取出source
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            good.setId(searchHit.getId());
            String goodName = (String) sourceAsMap.get("goodName");
            Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
            if (highlightFields != null) {
                HighlightField highlightField = highlightFields.get("goodName");
                if (highlightField != null) {
                    Text[] fragments = highlightField.getFragments();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (Text fragment : fragments) {
                        stringBuffer.append(fragment);
                    }
                    goodName = stringBuffer.toString();
                }
            }
            good.setGoodName(goodName);
            good.setDescription((String) sourceAsMap.get("description"));
            good.setImageUrl((String) sourceAsMap.get("imageUrl"));
            Double price = null;
            if (sourceAsMap.get("price") != null) {
                price = Double.valueOf(sourceAsMap.get("price").toString());
            }
            good.setPrice(price);
            goodList.add(good);
        }*/
        //        System.out.println(goodList);
    }
}
