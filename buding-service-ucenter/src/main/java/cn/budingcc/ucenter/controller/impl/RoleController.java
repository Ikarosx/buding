package cn.budingcc.ucenter.controller.impl;

import cn.budingcc.framework.domain.ucenter.BdRole;
import cn.budingcc.framework.domain.ucenter.BdUserRole;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.ucenter.controller.RoleControllerApi;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ikaros
 * @date 2020/1/31 16:42
 */
@RestController
@RequestMapping("ucenter")
public class RoleController implements RoleControllerApi {
    @Override
    @PostMapping("role")
    public ResponseResult insertRole(BdRole role) {
        return null;
    }
    
    @Override
    @DeleteMapping("role/{roleId}")
    public ResponseResult deleteRole(@PathVariable String roleId) {
        return null;
    }
    
    @Override
    @PutMapping("role/{roleId}")
    public ResponseResult updateRole(BdRole role, @PathVariable String roleId) {
        return null;
    }
    
    @Override
    public QueryResponseResult getRoleListByUserId(String userId) {
        return null;
    }
    
    @Override
    public QueryResponseResult getRoleList() {
        return null;
    }
    
    @Override
    public ResponseResult insertUserRole(BdUserRole userRole) {
        return null;
    }
}
