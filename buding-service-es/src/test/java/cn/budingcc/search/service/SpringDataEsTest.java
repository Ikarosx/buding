package cn.budingcc.search.service;

import cn.budingcc.es.ESApplication;
import cn.budingcc.es.domain.EsGood;
import cn.budingcc.es.service.EsShopService;
import cn.budingcc.framework.domain.shop.request.GoodSearchParam;
import cn.budingcc.framework.model.response.ResponseResult;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.LinkedList;
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
    private RestHighLevelClient restHighLevelClient;
    // @Autowired
    // ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    EsShopService esShopService;
    
    @Test
    public void deleteTest() throws IOException {
        ResponseResult responseResult = esShopService.deleteGood("123");
        System.out.println(responseResult);
    }
    
    @Test
    public void restHighLevelClientQueryTest() throws IOException {
        GoodSearchParam goodSearchParam = new GoodSearchParam();
        goodSearchParam.setKeyword("老婆");
        // goodSearchParam.setSort("price:-create_time:-");
        SearchRequest searchRequest = new SearchRequest("bd_good");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
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
            multiMatchQueryBuilder.maxExpansions(1);
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
        searchSourceBuilder.query(boolQueryBuilder);
        
        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("good_name");
        HighlightBuilder highlightBuilder1 = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("description");
        searchSourceBuilder.highlighter(highlightBuilder1);
        searchSourceBuilder.highlighter(highlightBuilder);
        
        // 分页
        int page = 1;
        int size = 10;
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
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        int length = Math.min(hits.getHits().length, 10);
        List<EsGood> result = new LinkedList<>();
        for (int i = 0; i < length; i++) {
            SearchHit hit = hits.getHits()[i];
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
            HighlightField highlightFieldDesc = hit.getHighlightFields().get("description");
            if (highlightFieldDesc != null) {
                Text[] fragments = highlightFieldDesc.fragments();
                StringBuilder builder = new StringBuilder();
                for (Text fragment : fragments) {
                    builder.append(fragment);
                }
                esGood.setDescription(builder.toString());
            }
            System.out.println(esGood);
            result.add(esGood);
        }
        long totalHits = hits.getTotalHits().value;
        
        System.out.println(totalHits);
    }
    
    @Test
    public void esTest() {
        /*NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        String testString = "我的老婆";
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("", "goodName", "description");
        // multiMatchQueryBuilder.minimumShouldMatch("70%");
        // multiMatchQueryBuilder.field("goodName", 10);
        // boolQueryBuilder.must(multiMatchQueryBuilder);
        // nativeSearchQueryBuilder.withQuery(multiMatchQueryBuilder);
        nativeSearchQueryBuilder.withHighlightFields(new HighlightBuilder.Field("goodName"));
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
            System.out.println(goods.getTotalPages());
            System.out.println(goods.getTotalElements());
            System.out.println(goods.getContent());
        } else {
            System.out.println("goods not found");
        }*/
        
    }
    
    @Test
    public void es2Test() {
        /*GoodSearchParam goodSearchParam = new GoodSearchParam();
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
        int size = 20;
        nativeSearchQueryBuilder.withPageable(PageRequest.of(page - 1, size));
        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font class='eslight'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("goodName");
        nativeSearchQueryBuilder.withHighlightBuilder(highlightBuilder);*/
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
