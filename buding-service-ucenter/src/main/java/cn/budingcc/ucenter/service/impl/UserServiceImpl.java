package cn.budingcc.ucenter.service.impl;

import cn.budingcc.framework.domain.ucenter.BdMenu;
import cn.budingcc.framework.domain.ucenter.BdRole;
import cn.budingcc.framework.domain.ucenter.BdUser;
import cn.budingcc.framework.domain.ucenter.BdUserRole;
import cn.budingcc.framework.domain.ucenter.extension.BdUserExtension;
import cn.budingcc.framework.domain.ucenter.extension.BdUserRoleExtension;
import cn.budingcc.framework.domain.ucenter.request.UserListRequest;
import cn.budingcc.framework.exception.ExceptionCast;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.QueryResult;
import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.ucenter.dao.BdMenuRepository;
import cn.budingcc.ucenter.dao.BdUserMapper;
import cn.budingcc.ucenter.dao.BdUserRepository;
import cn.budingcc.ucenter.dao.BdUserRoleRepository;
import cn.budingcc.ucenter.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Ikaros
 * @date 2020/1/30 19:51
 */
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private BdUserRepository bdUserRepository;
    @Autowired
    private BdUserMapper bdUserMapper;
    @Autowired
    private BdUserRoleRepository bdUserRoleRepository;
    @Autowired
    private BdMenuRepository bdMenuRepository;
    
    @Override
    public BdUserExtension getUserExtension(String username) {
        BdUser user = getUserByUserName(username);
        if (user == null) {
            return null;
        }
        List<BdMenu> bdMenuList = bdMenuRepository.selectPermissionByUserId(user.getId());
        BdUserExtension bdUserExtension = new BdUserExtension();
        // 用户权限
        BeanUtils.copyProperties(user, bdUserExtension);
        bdUserExtension.setPermissions(bdMenuList);
        return bdUserExtension;
    }
    
    @Override
    public QueryResponseResult listUsersByPage(int page, int size, UserListRequest userListRequest) {
        PageHelper.startPage(page, size);
        Page<BdUserRoleExtension> bdUserRoleExtensions = bdUserMapper.listUsersByPage(page, size, userListRequest);
        QueryResult<BdUserRoleExtension> queryResult = new QueryResult<>();
        queryResult.setList(bdUserRoleExtensions.getResult());
        queryResult.setTotal(bdUserRoleExtensions.getTotal());
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult insertUser(BdUserRoleExtension bdUser) {
        BdUser user = new BdUser();
        BeanUtils.copyProperties(bdUser, user);
        BdUser save = bdUserRepository.save(user);
        List<BdRole> bdRoleList = bdUser.getBdRoleList();
        for (BdRole bdRole : bdRoleList) {
            BdUserRole bdUserRole = new BdUserRole();
            bdUserRole.setUserId(save.getId());
            bdUserRole.setRoleId(bdRole.getId());
            bdUserRole.setCreateTime(new Date());
            bdUserRole.setUpdateTime(bdUserRole.getCreateTime());
            bdUserRoleRepository.save(bdUserRole);
        }
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteUser(String userId) {
        BdUserRole bdUserRole = new BdUserRole();
        bdUserRole.setUserId(userId);
        bdUserRoleRepository.delete(bdUserRole);
        bdUserRepository.deleteById(userId);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateUser(BdUserRoleExtension bdUser, String userId) {
        Optional<BdUser> userOptional = bdUserRepository.findById(userId);
        BdUser user = userOptional.orElseGet(null);
        if (user == null) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        BeanUtils.copyProperties(bdUser, user);
        bdUserRepository.save(user);
        bdUserRoleRepository.deleteByUserId(userId);
        List<BdRole> bdRoleList = bdUser.getBdRoleList();
        for (BdRole bdRole : bdRoleList) {
            BdUserRole bdUserRole = new BdUserRole();
            bdUserRole.setUserId(bdUser.getId());
            bdUserRole.setRoleId(bdRole.getId());
            bdUserRoleRepository.save(bdUserRole);
        }
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public BdUser getUserByUserName(String name) {
        return bdUserRepository.findByUserName(name);
    }
    
    private BdUser findByUserId(String userId) {
        Optional<BdUser> optional = bdUserRepository.findById(userId);
        return optional.orElseGet(null);
    }
}
