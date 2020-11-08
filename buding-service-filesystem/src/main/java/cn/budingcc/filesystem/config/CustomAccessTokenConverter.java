package cn.budingcc.filesystem.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 自定义解析JWT
 * @author Ikaros
 * @date 2020/11/03 19:34
 */
@Component
public class CustomAccessTokenConverter extends DefaultAccessTokenConverter {
    
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        OAuth2Authentication authentication = super.extractAuthentication(claims);
        authentication.setDetails(claims);
        return authentication;
    }
}