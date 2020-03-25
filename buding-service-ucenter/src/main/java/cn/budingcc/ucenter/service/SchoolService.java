package cn.budingcc.ucenter.service;

import cn.budingcc.framework.domain.ucenter.BdSchool;
import cn.budingcc.framework.domain.ucenter.BdSchoolSystem;
import cn.budingcc.framework.domain.ucenter.request.SchoolListRequest;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;

/**
 * @author Ikaros
 * @date 2020/2/12 16:27
 */
public interface SchoolService {
    QueryResponseResult listSchools(SchoolListRequest schoolListRequest);
    
    ResponseResult insertSchool(BdSchool school);
    
    ResponseResult deleteSchool(String schoolId);
    
    ResponseResult updateSchool(BdSchool school, String schoolId);
    
    QueryResponseResult listSchoolSystems();
    
    ResponseResult insertSchoolSystem(BdSchoolSystem schoolSystem);
    
    ResponseResult updateSchoolSystem(BdSchoolSystem schoolSystem, String schoolSystemId);
    
    ResponseResult deleteSchoolSystem(String schoolSystemId);
}
