package cn.budingcc.ucenter.controller.impl;

import cn.budingcc.framework.domain.ucenter.BdSchool;
import cn.budingcc.framework.domain.ucenter.BdSchoolSystem;
import cn.budingcc.framework.domain.ucenter.request.SchoolListRequest;
import cn.budingcc.framework.exception.ExceptionCast;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.ucenter.controller.SchoolControllerApi;
import cn.budingcc.ucenter.service.SchoolService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author Ikaros
 * @date 2020/2/12 16:26
 */
@RestController
@RequestMapping("ucenter")
public class SchoolController implements SchoolControllerApi {
    @Autowired
    SchoolService schoolService;
    @Override
    @GetMapping("school/list")
    public QueryResponseResult listSchools(SchoolListRequest schoolListRequest) {
        if (schoolListRequest == null) {
            schoolListRequest = new SchoolListRequest();
        }
        return schoolService.listSchools(schoolListRequest);
    }
    
    @Override
    @PostMapping("school")
    public ResponseResult insertSchool(@RequestBody BdSchool school) {
        schoolValidate(school);
        school.setCreateTime(new Date());
        school.setUpdateTime(school.getCreateTime());
        schoolService.insertSchool(school);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    @DeleteMapping("school/{schoolId}")
    public ResponseResult deleteSchool(@PathVariable String schoolId) {
        return schoolService.deleteSchool(schoolId);
    }
    
    @Override
    @PutMapping("school/{schoolId}")
    public ResponseResult updateSchool(@RequestBody BdSchool school, @PathVariable String schoolId) {
        schoolValidate(school);
        school.setUpdateTime(new Date());
        return schoolService.updateSchool(school, schoolId);
    }
    
    private void schoolValidate(@RequestBody BdSchool school) {
        String schoolName = school.getSchoolName().trim();
        String regex = "[\u4e00-\u9fa5]{1,10}";
        if (StringUtils.isEmpty(schoolName) || !Pattern.matches(regex, schoolName)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
    }
    
    @Override
    @GetMapping("/school/system/list")
    public QueryResponseResult listSchoolSystems() {
        return schoolService.listSchoolSystems();
    }
    
    @Override
    @PostMapping("/school/system")
    public ResponseResult insertSchoolSystem(@RequestBody BdSchoolSystem schoolSystem) {
        schoolSystemValidate(schoolSystem);
        return schoolService.insertSchoolSystem(schoolSystem);
    }
    
    @Override
    @PutMapping("/school/system/{schoolSystemId}")
    public ResponseResult updateSchoolSystem(@RequestBody BdSchoolSystem schoolSystem, @PathVariable String schoolSystemId) {
        schoolSystemValidate(schoolSystem);
        return schoolService.updateSchoolSystem(schoolSystem, schoolSystemId);
    }
    
    @Override
    @DeleteMapping("/school/system/{schoolSystemId}")
    public ResponseResult deleteSchoolSystem(@PathVariable String schoolSystemId) {
        return schoolService.deleteSchoolSystem(schoolSystemId);
    }
    
    private void schoolSystemValidate(BdSchoolSystem schoolSystem) {
        String systemName = schoolSystem.getSystemName();
        String regex = "[0-9a-zA-Z\u4e00-\u9fa5]{1,10}";
        schoolSystem.setSystemName(systemName.trim());
        if (StringUtils.isEmpty(systemName) || !Pattern.matches(regex, systemName)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
    }
}
