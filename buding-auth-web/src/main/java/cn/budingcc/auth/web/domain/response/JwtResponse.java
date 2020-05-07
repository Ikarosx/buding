package cn.budingcc.auth.web.domain.response;

import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.framework.model.response.ResultCode;
import lombok.Data;

/**
 * @author Ikaros
 * @date 2020/3/26 21:35
 */
@Data
public class JwtResponse extends ResponseResult {
    String token;
    
    public JwtResponse(ResultCode resultCode, String token) {
        super(resultCode);
        this.token = token;
    }
}
