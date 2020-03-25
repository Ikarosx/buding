package cn.budingcc.cms.dao;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Ikaros
 * @date 2020/3/1 11:59
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFsTest {
    @Autowired
    GridFsTemplate gridFsTemplate;
    @Autowired
    CmsPageRepository cmsPageRepository;
    
    @Test
    public void repositoryTest(){
        cmsPageRepository.findAll();
    }
    

    
    @Test
    public void deleteTest() {
        Query query = new Query();
        Criteria criteria = GridFsCriteria.where("_id").is("5e5b35750cbd1c7dea2bc362");
        query.addCriteria(criteria);
        gridFsTemplate.delete(query);
    }
    
    @Test
    public void getTest() {
        Query query = new Query();
        Criteria criteria = GridFsCriteria.where("_id").is("5e5b4ce5e93fd63b0e5b3eff");
        query.addCriteria(criteria);
        // 跟删除一模一样,只是调用方法不同
        GridFSFile file = gridFsTemplate.findOne(query);
    }
}
