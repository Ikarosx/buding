package cn.budingcc.framework.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Ikaros
 * @date 2020/1/26 18:08
 */
@Data
@ToString
@NoArgsConstructor
public class ResponseResult implements Response {
    boolean success = SUCCESS;
    
    int code = SUCCESS_CODE;
    
    String message;
    
    public ResponseResult(ResultCode resultCode) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }
    
    public static ResponseResult success() {
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    public static ResponseResult fail() {
        return new ResponseResult(CommonCodeEnum.FAIL);
    }
    
}
