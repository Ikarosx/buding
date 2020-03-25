package cn.budingcc.ucenter.service.impl;

import cn.budingcc.framework.domain.ucenter.BdRole;
import cn.budingcc.framework.domain.ucenter.BdUserRole;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.QueryResult;
import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.ucenter.dao.BdRoleRepository;
import cn.budingcc.ucenter.dao.BdUserRoleRepository;
import cn.budingcc.ucenter.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/1/31 16:50
 */
@Service("RoleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    BdRoleRepository bdRoleRepository;
    @Autowired
    BdUserRoleRepository bdUserRoleRepository;
    
    @Override
    public ResponseResult insertRole(BdRole role) {
        bdRoleRepository.save(role);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult deleteRole(String roleId) {
        bdRoleRepository.deleteById(roleId);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult updateRole(BdRole role, String roleId) {
        bdRoleRepository.save(role);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public QueryResponseResult getRoleListByUserId(String userId) {
        BdUserRole bdUserRole = new BdUserRole();
        bdUserRole.setUserId(userId);
        Example<BdUserRole> example = Example.of(bdUserRole);
        List<BdUserRole> userRoleList = bdUserRoleRepository.findAll(example);
        QueryResult<BdUserRole> queryResult = new QueryResult<>();
        queryResult.setTotal(userRoleList.size());
        queryResult.setList(userRoleList);
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
    
    @Override
    public QueryResponseResult getRoleList() {
        
        return null;
    }
    
    @Override
    public ResponseResult insertUserRole(BdUserRole userRole) {
        bdUserRoleRepository.save(userRole);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
}
