package cn.budingcc.cms.dao;

import cn.budingcc.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Ikaros
 * @date 2020/3/1 10:53
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {
}
