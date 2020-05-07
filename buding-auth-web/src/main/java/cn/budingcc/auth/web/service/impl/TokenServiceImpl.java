package cn.budingcc.auth.web.service.impl;

import cn.budingcc.auth.web.domain.TokenInfo;
import cn.budingcc.auth.web.service.TokenService;
import cn.budingcc.auth.web.util.CookieUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Ikaros
 * @date 2020/3/26 10:35
 */
@Service
@Slf4j
public class TokenServiceImpl implements TokenService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Override
    public boolean saveTokenToRedis(TokenInfo tokenInfo) {
        String key = "user_token:" + tokenInfo.getJit();
        String value = JSON.toJSONString(tokenInfo);
        try {
            stringRedisTemplate.boundValueOps(key).set(value, tokenInfo.getExpires_in(), TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("save token to redis fail", e);
            return false;
        }
        return true;
    }
    
    @Override
    public boolean deleteToken(String jit) {
        try {
            stringRedisTemplate.delete("user_token:" + jit);
        } catch (Exception e) {
            log.error("delete token fail", e);
            return false;
        }
        return true;
    }
    
    @Override
    public TokenInfo getTokenByJit(String jit) {
        String infoString = stringRedisTemplate.opsForValue().get("user_token:" + jit);
        TokenInfo tokenInfo = (TokenInfo) JSON.parse(infoString);
        return tokenInfo;
    }
    
    @Override
    public String getJitFromCookie() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> map = CookieUtil.readCookie(request, "jit");
        String jit = map.get("jit");
        return jit;
    }
}
