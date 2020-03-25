package cn.budingcc.framework.model.response;

/**
 * @author Ikaros
 * @date 2020/1/26 16:58
 */

public enum CommonCodeEnum implements ResultCode {
    
    /**
     * 通用错误代码 10000
     */
    SUCCESS(true, 10000, "操作成功！"),
    INVALID_PARAM(false, 10001, "非法参数"),
    DATA_INTEGRITY_VIOLATION_EXCEPTION(false, 10002,"数据完整性约束异常"),
    JSON_PARSE_ERROR0(false, 10003,"json解析失败"),
    FILE_SIZE_LIMIT_EXCEEDED(false, 10004, "上传文件过大"),
    IO_EXCEPTION(false, 10005, "IO异常"),
    FAIL(false, 11111, "操作失败！"),
    SERVER_ERROR(false, 99999, "系统繁忙，请稍后重试！");
    
    private boolean success;
    private int code;
    private String message;
    
    CommonCodeEnum(boolean success, int code, String message) {
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
