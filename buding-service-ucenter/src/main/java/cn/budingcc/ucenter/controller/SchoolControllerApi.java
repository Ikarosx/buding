package cn.budingcc.ucenter.controller;

import cn.budingcc.framework.domain.ucenter.BdSchool;
import cn.budingcc.framework.domain.ucenter.BdSchoolSystem;
import cn.budingcc.framework.domain.ucenter.request.SchoolListRequest;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Ikaros
 * @date 2020/2/12 16:17
 */
@Api("学校接口")
public interface SchoolControllerApi {
    
    @ApiOperation("查询学校列表")
    QueryResponseResult listSchools(SchoolListRequest schoolListRequest);
    
    @ApiOperation("添加学校")
    ResponseResult insertSchool(BdSchool school);
    
    @ApiOperation("删除学校")
    ResponseResult deleteSchool(String schoolId);
    
    @ApiOperation("修改学校")
    ResponseResult updateSchool(BdSchool school, String schoolId);
    
    @ApiOperation("查询学校系统类别")
    QueryResponseResult listSchoolSystems();
    
    @ApiOperation("添加学校系统类别")
    ResponseResult insertSchoolSystem(BdSchoolSystem schoolSystem);
    
    @ApiOperation("修改学校系统类别")
    ResponseResult updateSchoolSystem(BdSchoolSystem schoolSystem, String schoolSystemId);
    
    @ApiOperation("删除学校系统类别")
    ResponseResult deleteSchoolSystem(String schoolSystemId);
}
