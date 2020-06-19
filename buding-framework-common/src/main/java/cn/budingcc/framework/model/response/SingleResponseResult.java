package cn.budingcc.framework.model.response;

import lombok.Data;

/**
 * @author Ikaros
 * @date 2020/6/19 13:15
 */
@Data
public class SingleResponseResult<T> extends ResponseResult {
    private T data;
    
    public SingleResponseResult(ResultCode resultCode, T data) {
        super(resultCode);
        this.data = data;
    }
}
