package cn.budingcc.ucenter.controller;

import cn.budingcc.framework.domain.ucenter.BdUser;
import cn.budingcc.framework.domain.ucenter.extension.BdUserExtension;
import cn.budingcc.framework.domain.ucenter.extension.BdUserRoleExtension;
import cn.budingcc.framework.domain.ucenter.request.UserListRequest;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.framework.model.response.SingleResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Ikaros
 * @date 2020/1/30 18:57
 */
@Api("用户中心接口")
public interface UserControllerApi {
    @ApiOperation("查询用户额外信息")
    BdUserExtension getUserExtension(String username);
    
    @ApiOperation("根据用户名查询用户")
    BdUser getUserByUserName(String name);
    
    @ApiOperation("根据学生ID查询用户")
    SingleResponseResult getUserByStudentId(String studentId);
    
    @ApiOperation("查询用户列表")
    QueryResponseResult listUsersByPage(int page, int size, UserListRequest userListRequest);
    
    @ApiOperation("添加用户")
    ResponseResult insertUser(BdUserRoleExtension bdUser);
    
    @ApiOperation("删除用户")
    ResponseResult deleteUser(String userId);
    
    @ApiOperation("更新用户")
    ResponseResult updateUser(BdUserRoleExtension bdUser, String userId);
    
    @ApiOperation("通过用户ID获取简单用户信息")
    SingleResponseResult getUserByUserId(String id);
}
