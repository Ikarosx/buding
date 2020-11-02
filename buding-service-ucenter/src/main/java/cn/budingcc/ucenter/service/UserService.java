package cn.budingcc.ucenter.service;

import cn.budingcc.framework.domain.ucenter.BdUser;
import cn.budingcc.framework.domain.ucenter.extension.BdUserExtension;
import cn.budingcc.framework.domain.ucenter.extension.BdUserRoleExtension;
import cn.budingcc.framework.domain.ucenter.request.UserListRequest;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.ucenter.domain.SimpleUser;

/**
 * @author Ikaros
 * @date 2020/1/30 19:49
 */
public interface UserService {
    BdUserExtension getUserExtension(String username);
    
    QueryResponseResult listUsersByPage(int page, int size, UserListRequest userListRequest);
    
    ResponseResult insertUser(BdUserRoleExtension bdUser);
    
    ResponseResult deleteUser(String userId);
    
    ResponseResult updateUser(BdUserRoleExtension bdUser, String userId);
    
    BdUser getUserByUserName(String name);
    
    SimpleUser getUserByStudentId(String studentId);
}
