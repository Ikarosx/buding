package cn.budingcc.framework.domain.shop.response;

import cn.budingcc.framework.model.response.ResultCode;

/**
 * @author Ikaros
 * @date 2020/1/29 14:22
 */
public enum ShopEnum implements ResultCode {
    /**
     * 商品相关错误代码 22000
     */
    FOLLOW_SELF_ERROR(false, 22001, "不能关注自己"),
    GOOD_NOT_FOUND(false, 22002, "商品不存在");
    
    private boolean success;
    private int code;
    private String message;
    
    ShopEnum(boolean success, int code, String message) {
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
