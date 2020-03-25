package cn.budingcc.ucenter.service;

import cn.budingcc.framework.domain.ucenter.BdRole;
import cn.budingcc.framework.domain.ucenter.BdUserRole;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;

/**
 * @author Ikaros
 * @date 2020/1/31 16:49
 */
public interface RoleService {
    ResponseResult insertRole(BdRole role);
    
    ResponseResult deleteRole(String roleId);
    
    ResponseResult updateRole(BdRole role, String roleId);
    
    QueryResponseResult getRoleListByUserId(String userId);
    
    QueryResponseResult getRoleList();
    
    ResponseResult insertUserRole(BdUserRole userRole);
}
