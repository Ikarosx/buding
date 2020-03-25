package cn.budingcc.auth.web.domain.response;

import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.framework.model.response.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ikaros
 * @date 2020/3/16 11:28
 */
@Data
@NoArgsConstructor
public class LoginResponse extends ResponseResult {
    private String token;
    
    public LoginResponse(ResultCode resultCode, String token) {
        super(resultCode);
        this.token = token;
    }
}
