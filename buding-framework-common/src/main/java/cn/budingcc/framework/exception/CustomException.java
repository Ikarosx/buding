package cn.budingcc.framework.exception;

import cn.budingcc.framework.model.response.ResultCode;

/**
 * @author Ikaros
 * @date 2020/1/26 18:19
 */
public class CustomException extends RuntimeException {
    private ResultCode resultCode;
    
    CustomException(ResultCode resultCode) {
        super("错误代码：" + resultCode.code() + "错误信息：" + resultCode.message());
        this.resultCode = resultCode;
    }
    
    public ResultCode getResultCode() {
        return resultCode;
    }
}
