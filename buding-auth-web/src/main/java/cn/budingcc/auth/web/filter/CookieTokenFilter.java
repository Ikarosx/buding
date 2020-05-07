package cn.budingcc.auth.web.filter;

import cn.budingcc.auth.web.domain.TokenInfo;
import cn.budingcc.auth.web.exception.AuthExceptionEnum;
import cn.budingcc.auth.web.service.CookieService;
import cn.budingcc.framework.model.response.ResponseResult;
import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
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
// @Component
@Slf4j
public class CookieTokenFilter extends ZuulFilter {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private CookieService cookieService;
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
        HttpServletRequest request = currentContext.getRequest();
        return currentContext.sendZuulResponse() && !StringUtils.startsWithIgnoreCase(request.getRequestURI(), "/oauth");
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
                    // cookie
                    currentContext.addZuulRequestHeader("Authorization", "bearer " + tokenInfo.getAccess_token());
                } catch (Exception e) {
                    refreshFail();
                }
            } else {
                refreshFail();
            }
        }
        return null;
    }
    
    public void refreshFail() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        currentContext.setResponseStatusCode(200);
        currentContext.setSendZuulResponse(false);
        ResponseResult responseResult = new ResponseResult(AuthExceptionEnum.LOGIN_RETRY);
        String s = JSON.toJSONString(responseResult);
        HttpServletResponse response = currentContext.getResponse();
        response.setContentType("application/json");
        currentContext.setResponseBody(s);
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
