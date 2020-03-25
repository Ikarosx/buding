package cn.budingcc.ucenter.service.impl;

import cn.budingcc.framework.domain.ucenter.BdSchool;
import cn.budingcc.framework.domain.ucenter.BdSchoolSystem;
import cn.budingcc.framework.domain.ucenter.request.SchoolListRequest;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.QueryResult;
import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.ucenter.dao.BdSchoolRepository;
import cn.budingcc.ucenter.dao.BdSchoolSystemRepository;
import cn.budingcc.ucenter.service.SchoolService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/2/12 16:31
 */
@Service("SchoolService")
public class SchoolServiceImpl implements SchoolService {
    @Autowired
    BdSchoolRepository bdSchoolRepository;
    @Autowired
    BdSchoolSystemRepository bdSchoolSystemRepository;
    
    @Override
    public QueryResponseResult listSchools(SchoolListRequest schoolListRequest) {
        BdSchool school = new BdSchool();
        Integer state = schoolListRequest.getState();
        if (state != null) {
            school.setState(state);
        }
        Integer systemType = schoolListRequest.getSystemType();
        if (systemType != null) {
            school.setSystemType(systemType);
        }
        String schoolName = schoolListRequest.getSchoolName();
        if (!StringUtils.isEmpty(schoolName)) {
            school.setSchoolName(schoolName);
        }
        ExampleMatcher matching = ExampleMatcher.matching().withMatcher("schoolName", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<BdSchool> example = Example.of(school, matching);
        List<BdSchool> bdSchools = bdSchoolRepository.findAll(example);
        QueryResult<BdSchool> queryResult = new QueryResult<>();
        queryResult.setTotal(bdSchools.size());
        queryResult.setList(bdSchools);
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
    
    @Override
    public ResponseResult insertSchool(BdSchool school) {
        bdSchoolRepository.save(school);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult deleteSchool(String schoolId) {
        bdSchoolRepository.deleteById(schoolId);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult updateSchool(BdSchool school, String schoolId) {
        bdSchoolRepository.save(school);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public QueryResponseResult listSchoolSystems() {
        List<BdSchoolSystem> schoolSystemList = bdSchoolSystemRepository.findAll();
        QueryResult<BdSchoolSystem> queryResult = new QueryResult<>();
        queryResult.setList(schoolSystemList);
        queryResult.setTotal(schoolSystemList.size());
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
    
    @Override
    public ResponseResult insertSchoolSystem(BdSchoolSystem schoolSystem) {
        bdSchoolSystemRepository.save(schoolSystem);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult updateSchoolSystem(BdSchoolSystem schoolSystem, String schoolSystemId) {
        bdSchoolSystemRepository.save(schoolSystem);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult deleteSchoolSystem(String schoolSystemId) {
        bdSchoolSystemRepository.deleteById(schoolSystemId);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
}
