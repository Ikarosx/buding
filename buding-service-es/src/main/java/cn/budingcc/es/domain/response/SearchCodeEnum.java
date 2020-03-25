package cn.budingcc.es.domain.response;

import cn.budingcc.framework.model.response.ResultCode;

/**
 * @author Ikaros
 * @date 2020/2/24 0:22
 */
public enum SearchCodeEnum implements ResultCode {
    /**
     * 搜索错误代码 24000
     */
    SEARCH_ERROR(false, 24001, "搜索出错");
    private boolean success;
    private int code;
    private String message;
    
    SearchCodeEnum(boolean success, int code, String message) {
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
