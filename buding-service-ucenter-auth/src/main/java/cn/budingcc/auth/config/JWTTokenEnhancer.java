package cn.budingcc.auth.config;

import cn.budingcc.auth.domain.BdUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

public class JWTTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> info = new HashMap<>();
        if (oAuth2Authentication.getUserAuthentication().getPrincipal() instanceof BdUser) {
            BdUser bdUser = (BdUser) oAuth2Authentication.getUserAuthentication().getPrincipal();
            info.put("user_pic", bdUser.getUserPic());
            info.put("school_id", bdUser.getSchoolId());
            info.put("nick_name", bdUser.getNickName());
            info.put("id", bdUser.getId());
            info.put("username", bdUser.getUsername());
            info.put("student_id", bdUser.getStudentId());
            ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);
        }
        return oAuth2AccessToken;
    }
}