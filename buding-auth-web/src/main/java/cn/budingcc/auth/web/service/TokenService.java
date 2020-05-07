package cn.budingcc.auth.web.service;

import cn.budingcc.auth.web.domain.TokenInfo;

/**
 * @author Ikaros
 * @date 2020/3/26 10:28
 */
public interface TokenService {
    boolean saveTokenToRedis(TokenInfo tokenInfo);
    
    boolean deleteToken(String jit);
    
    TokenInfo getTokenByJit(String jit);
    
    String getJitFromCookie();
}
