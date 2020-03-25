package cn.budingcc.auth.web.controller;

import cn.budingcc.framework.model.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ikaros
 * @date 2020/3/18 9:33
 */
public interface AuthControllerApi {
    void callback(String code, String state, HttpServletRequest request, HttpServletResponse response);
    
    ResponseResult logout(HttpServletRequest request);
    
}
