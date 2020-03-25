package cn.budingcc.ucenter.controller;

import cn.budingcc.framework.domain.ucenter.BdRole;
import cn.budingcc.framework.domain.ucenter.BdUserRole;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Ikaros
 * @date 2020/1/30 22:33
 */
@Api("角色接口")
public interface RoleControllerApi {
    @ApiOperation("添加角色")
    ResponseResult insertRole(BdRole role);
    
    @ApiOperation("删除角色")
    ResponseResult deleteRole(String roleId);
    
    @ApiOperation("修改角色")
    ResponseResult updateRole(BdRole role, String roleId);
    
    @ApiOperation("查看用户对应角色")
    QueryResponseResult getRoleListByUserId(String userId);
    
    @ApiOperation("查看所有角色")
    QueryResponseResult getRoleList();
    
    @ApiOperation("关联用户与角色")
    ResponseResult insertUserRole(BdUserRole userRole);
    
    
}
