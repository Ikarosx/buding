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
    LOGIN_RETRY(false, 30002, "请重新登陆"),
    FRESH_FAIL(false, 30003, "刷新令牌失败"),
    SAVE_TOKEN_TO_REDIS_FAIL(false, 30004, "保存token到redis失败"),
    GET_TOKEN_BY_JIT_FAIL(false, 30005, "通过JIT获取token失败");
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
