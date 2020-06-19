package cn.budingcc.gateway.filter;

import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.gateway.domain.TokenInfo;
import cn.budingcc.gateway.exception.AuthExceptionEnum;
import cn.budingcc.gateway.service.TokenService;
import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ikaros
 * @date 2020/3/25 9:14
 */
@Component
@Order(value = 2)
@Slf4j
public class CookieTokenFilter extends ZuulFilter {
    
    @Autowired
    private TokenService tokenService;
    
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
        String jti = getCookie("jti");
        if (StringUtils.isBlank(jti)) {
            // 没有携带jti
            relogin();
            return null;
        }
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.startsWithIgnoreCase(authorizationHeader, "bearer ")) {
            relogin();
            return null;
        }
        TokenInfo tokenInfo = tokenService.getTokenByJit(jti);
        if (tokenInfo == null) {
            relogin();
            return null;
        }
        // String accessToken = getCookie("buding_access_token");
        // if (StringUtils.isNotBlank(accessToken)) {
        //     currentContext.addZuulRequestHeader("Authorization", "bearer " + accessToken);
        // } else {
        //     String refreshToken = getCookie("buding_refresh_token");
        //     if (StringUtils.isNotBlank(refreshToken)) {
        //         HttpHeaders httpHeaders = new HttpHeaders();
        //         httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //         httpHeaders.setBasicAuth("webBuding", "123456");
        //         MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        //         params.add("grant_type", "refresh_token");
        //         params.add("refresh_token", refreshToken);
        //         HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);
        //         ResponseEntity<TokenInfo> responseEntity = null;
        //         try {
        //             responseEntity = restTemplate.exchange(authServerUrl + "/oauth/token", HttpMethod.POST, httpEntity, TokenInfo.class);
        //             TokenInfo tokenInfo = responseEntity.getBody();
        //             // cookie
        //             currentContext.addZuulRequestHeader("Authorization", "bearer " + tokenInfo.getAccess_token());
        //         } catch (Exception e) {
        //             refreshFail();
        //         }
        //     } else {
        //         refreshFail();
        //     }
        // }
        return null;
    }
    
    public void relogin() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        currentContext.setResponseStatusCode(200);
        currentContext.setSendZuulResponse(false);
        ResponseResult responseResult = new ResponseResult(AuthExceptionEnum.LOGIN_RETRY);
        String s = JSON.toJSONString(responseResult);
        HttpServletResponse response = currentContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        currentContext.setResponseBody(s);
    }
    
    private String getCookie(String cookieName) {
        String result = null;
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "";
        }
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookieName, cookie.getName())) {
                result = cookie.getValue();
                break;
            }
        }
        return result;
    }
}
