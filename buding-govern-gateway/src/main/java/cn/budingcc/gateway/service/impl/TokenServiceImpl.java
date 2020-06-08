package cn.budingcc.gateway.service.impl;

import cn.budingcc.gateway.domain.TokenInfo;
import cn.budingcc.gateway.service.TokenService;
import cn.budingcc.gateway.util.CookieUtil;
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
        String key = "user_token:" + tokenInfo.getJti();
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
    public boolean deleteToken(String jti) {
        try {
            stringRedisTemplate.delete("user_token:" + jti);
        } catch (Exception e) {
            log.error("delete token fail", e);
            return false;
        }
        return true;
    }
    
    @Override
    public TokenInfo getTokenByJit(String jti) {
        String infoString = stringRedisTemplate.opsForValue().get("user_token:" + jti);
        TokenInfo tokenInfo = JSON.parseObject(infoString, TokenInfo.class);
        return tokenInfo;
    }
    
    @Override
    public String getJitFromCookie() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> map = CookieUtil.readCookie(request, "jti");
        String jti = map.get("jti");
        return jti;
    }
}
