package cn.budingcc.auth.web.filter;

import cn.budingcc.auth.web.domain.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ikaros
 * @date 2020/3/25 9:14
 */
@Component
@Slf4j
public class CookieTokenFilter extends ZuulFilter {
    @Autowired
    RestTemplate restTemplate;
    @Value("${buding.auth.serverUrl}")
    private String authServerUrl;
    
    @Override
    public String filterType() {
        return "pre";
    }
    
    @Override
    public int filterOrder() {
        return 2;
    }
    
    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        return currentContext.sendZuulResponse();
    }
    
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();
        String accessToken = getCookie("buding_access_token");
        if (StringUtils.isNotBlank(accessToken)) {
            currentContext.addZuulRequestHeader("Authorization", "bearer " + accessToken);
        } else {
            String refreshToken = getCookie("buding_refresh_token");
            if (StringUtils.isNotBlank(refreshToken)) {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                httpHeaders.setBasicAuth("webBuding", "123456");
                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.add("grant_type", "refresh_token");
                params.add("refresh_token", refreshToken);
                HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);
                ResponseEntity<TokenInfo> responseEntity = null;
                try {
                    responseEntity = restTemplate.exchange(authServerUrl + "/oauth/token", HttpMethod.POST, httpEntity, TokenInfo.class);
                    TokenInfo tokenInfo = responseEntity.getBody();
                    Cookie budingAccessToken = new Cookie("buding_access_token", tokenInfo.getAccess_token());
                    budingAccessToken.setMaxAge(tokenInfo.getExpires_in().intValue() - 3);
                    budingAccessToken.setDomain("budingcc.cn");
                    budingAccessToken.setPath("/");
                    response.addCookie(budingAccessToken);
                    Cookie budingRefreshToken = new Cookie("buding_refresh_token", tokenInfo.getRefresh_token());
                    budingAccessToken.setMaxAge(259200);
                    budingAccessToken.setDomain("budingcc.cn");
                    budingAccessToken.setPath("/");
                    response.addCookie(budingRefreshToken);
                    currentContext.addZuulRequestHeader("Authorization", "bearer " + tokenInfo.getAccess_token());
                } catch (Exception e) {
                    currentContext.setSendZuulResponse(false);
                    currentContext.setResponseBody("{\"success\":false,\"code\":40001, \"message\":\"refresh fail\"}");
                    currentContext.getResponse().setContentType("application/json");
                }
            } else {
                currentContext.setSendZuulResponse(false);
                currentContext.setResponseBody("{\"success\":false,\"code\":40001, \"message\":\"refresh fail\"}");
                currentContext.getResponse().setContentType("application/json");
            }
        }
        return null;
    }
    
    private String getCookie(String cookieName) {
        String result = null;
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookieName, cookie.getName())) {
                result = cookie.getValue();
                break;
            }
        }
        return result;
    }
}
