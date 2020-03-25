package cn.budingcc.auth.config;


import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ikaros
 * @date 2020/3/19 11:09
 */
@Component
public class OAuth2LogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUri = request.getParameter("redirect_uri");
        if (StringUtils.isNotBlank(redirectUri)) {
            response.sendRedirect(redirectUri);
        }
    }
}
