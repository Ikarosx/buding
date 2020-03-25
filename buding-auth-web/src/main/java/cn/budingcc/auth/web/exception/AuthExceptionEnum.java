package cn.budingcc.auth.web.exception;

import cn.budingcc.framework.model.response.ResultCode;

/**
 * @author Ikaros
 * @date 2020/3/16 13:21
 */
public enum AuthExceptionEnum implements ResultCode {
    
    /**
     * 授权错误代码
     * 30000
     */
    USER_OR_PASSWORD_ERROR(false, 30001, "用户名或密码错误"),
    NOT_LOGIN(false, 30002, "当前未登录");
    private boolean success;
    private int code;
    private String message;
    
    AuthExceptionEnum(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
    
    @Override
    public boolean success() {
        return success;
    }
    
    @Override
    public int code() {
        return code;
    }
    
    @Override
    public String message() {
        return message;
    }
}
