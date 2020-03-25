package cn.budingcc.auth.web.domain;

import lombok.Data;

import java.time.LocalDateTime;

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
    
    private LocalDateTime expireTime;
    
    public TokenInfo init() {
        // 预防有些误差
        expireTime = LocalDateTime.now().plusSeconds(expires_in - 3);
        return this;
    }
    
    public boolean isExpired() {
        return expireTime.isBefore(LocalDateTime.now());
    }
}
