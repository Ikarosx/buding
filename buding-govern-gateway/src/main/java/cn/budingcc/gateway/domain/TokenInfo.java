package cn.budingcc.gateway.domain;

import lombok.Data;

/**
 * @author Ikaros
 * @date 2020/3/16 11:44
 */
@Data
public class TokenInfo {
    private String access_token;
    private String token_type;
    private Long expires_in;
    private String scope;
    private String refresh_token;
    private String jti;
}
