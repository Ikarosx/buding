package cn.budingcc.ucenter.controller.impl;

import cn.budingcc.framework.domain.ucenter.BdUser;
import cn.budingcc.framework.domain.ucenter.extension.BdUserExtension;
import cn.budingcc.framework.domain.ucenter.extension.BdUserRoleExtension;
import cn.budingcc.framework.domain.ucenter.request.UserListRequest;
import cn.budingcc.framework.exception.ExceptionCast;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.ucenter.controller.UserControllerApi;
import cn.budingcc.ucenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author Ikaros
 * @date 2020/1/30 19:47
 */
@RestController
@RequestMapping("ucenter")
public class UserController implements UserControllerApi {
    @Autowired
    UserService userService;
    
    @Override
    @GetMapping("user/userExtension")
    public BdUserExtension getUserExtension(@RequestParam String userName) {
        return userService.getUserExtension(userName);
    }
    
    @Override
    @GetMapping("user")
    public BdUser getUserByUserName(@RequestParam @NotBlank String name) {
        return userService.getUserByUserName(name);
    }
    
    @Override
    @GetMapping("user/list/{page}/{size}")
    public QueryResponseResult listUsersByPage(@PathVariable int page, @PathVariable int size, UserListRequest userListRequest) {
        if (page < 0) {
            page = 0;
        }
        if (size < 0) {
            size = 10;
        }
        if (userListRequest == null) {
            userListRequest = new UserListRequest();
        }
        return userService.listUsersByPage(page, size, userListRequest);
    }
    
    @Override
    @PostMapping("user")
    public ResponseResult insertUser(@RequestBody BdUserRoleExtension bdUser) {
        // 参数校验
        userValidate(bdUser);
        bdUser.setId(null);
        bdUser.setCreateTime(new Date());
        bdUser.setUpdateTime(bdUser.getCreateTime());
        return userService.insertUser(bdUser);
    }
    
    @Override
    @DeleteMapping("user/{userId}")
    public ResponseResult deleteUser(@PathVariable String userId) {
        if (StringUtils.isEmpty(userId)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        return userService.deleteUser(userId);
    }
    
    @Override
    @PutMapping("user/{userId}")
    public ResponseResult updateUser(@RequestBody BdUserRoleExtension bdUser,@PathVariable String userId) {
        String nickName = bdUser.getNickName();
        String nickNameRegex = "[0-9a-zA-Z\u4e00-\u9fa5]{1,10}";
        if (!StringUtils.isEmpty(nickName) && !Pattern.matches(nickNameRegex, nickName)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        Integer state = bdUser.getState();
        if (state != null && (state < 0 || state > 2)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        Integer sex = bdUser.getSex();
        if (sex != null && (sex < 0 || sex > 1)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        return userService.updateUser(bdUser, userId);
    }
    
    private void userValidate(@RequestBody BdUserRoleExtension bdUser) {
        String studentId = bdUser.getStudentId();
        String studentIdRegex = "[0-9a-zA-Z]{1,20}";
        if (StringUtils.isEmpty(studentId) || !Pattern.matches(studentIdRegex, studentId)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        String userName = bdUser.getUserName();
        String userNameRegex = "[a-zA-Z\u4e00-\u9fa5]{1,10}";
        if (StringUtils.isEmpty(userName) || !Pattern.matches(userNameRegex, userName)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        String nickName = bdUser.getNickName();
        String nickNameRegex = "[0-9a-zA-Z\u4e00-\u9fa5]{1,10}";
        if (StringUtils.isEmpty(nickName) || !Pattern.matches(nickNameRegex, nickName)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        Integer state = bdUser.getState();
        if (state == null || state < 0 || state > 2) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        Integer sex = bdUser.getSex();
        if (sex == null || sex < 0 || sex > 1) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        String schoolId = bdUser.getSchoolId();
        if (StringUtils.isEmpty(schoolId)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
    }
    
    
}
