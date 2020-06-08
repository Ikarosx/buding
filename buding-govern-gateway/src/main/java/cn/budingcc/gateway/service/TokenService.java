package cn.budingcc.gateway.service;

import cn.budingcc.gateway.domain.TokenInfo;

/**
 * @author Ikaros
 * @date 2020/3/26 10:28
 */
public interface TokenService {
    boolean saveTokenToRedis(TokenInfo tokenInfo);
    
    boolean deleteToken(String jti);
    
    TokenInfo getTokenByJit(String jti);
    
    String getJitFromCookie();
}
