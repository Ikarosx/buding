package cn.budingcc.framework.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * 资源服务器解析JWT工具类
 *
 * @author Ikarosx
 * @date 2020/11/4 22:00
 */
public class SecurityUtils {
    public static Map getBdUserFromAuth() {
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return (Map) details.getDecodedDetails();
    }
    
    public static String getUsernameFromAuth() {
        Object username = getBdUserFromAuth().get("username");
        Assert.notNull(username, "username in jwt must not be null.");
        return (String) username;
    }
    
    public static String getIdFromAuth() {
        Object id = getBdUserFromAuth().get("id");
        Assert.notNull(id, "id in jwt must not be null.");
        return (String) id;
    }
}
