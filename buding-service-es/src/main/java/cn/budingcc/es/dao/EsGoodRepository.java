package cn.budingcc.es.dao;

import cn.budingcc.es.domain.EsGood;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author Ikaros
 * @date 2020/2/24 17:20
 */
public interface EsGoodRepository extends ElasticsearchRepository<EsGood, String> {
}
