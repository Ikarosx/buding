package cn.budingcc.auth.web.filter;

import cn.budingcc.auth.web.domain.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ikaros
 * @date 2020/03/19 13:17
 */
@Component
@Slf4j
public class SessionTokenFilter extends ZuulFilter {
    
    @Value("${buding.auth.serverUrl}")
    private String authServerUrl;
    private RestTemplate restTemplate = new RestTemplate();
    
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
        RequestContext context = RequestContext.getCurrentContext();
        return context.sendZuulResponse();
    }
    
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();
        TokenInfo tokenInfo = (TokenInfo) request.getSession().getAttribute("tokenInfo");
        // TODO
        // 当auth-web前端服务器重启之后，session清空
        // 导致用户来访问时tokenInfo判断为空，不带上Authorization头
        // 跑到gateway时直接就可以访问
        // 这导致了跟token无关就可以直接访问
        if (tokenInfo != null) {
            //            try {
            //                log.info("auth web session is invalid, redirect to oauth/me");
            //                response.sendRedirect("http://admin.budingcc.cn:40010/oauth/me");
            //            } catch (IOException e) {
            //                e.printStackTrace();
            //            }
            //            currentContext.setSendZuulResponse(false);
            //        } else
            log.info(request.getRequestURI());
            log.info(tokenInfo.toString());
            if (tokenInfo.isExpired()) {
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                httpHeaders.setBasicAuth("webBuding", "123456");
                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.add("grant_type", "refresh_token");
                params.add("refresh_token", tokenInfo.getRefresh_token());
                HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);
                ResponseEntity<TokenInfo> responseEntity = null;
                log.info("refresh uri is:{}", authServerUrl + "/oauth/token");
                try {
                    responseEntity = restTemplate.exchange(authServerUrl + "/oauth/token", HttpMethod.POST, httpEntity, TokenInfo.class);
                    tokenInfo = responseEntity.getBody();
                    request.getSession().setAttribute("tokenInfo", responseEntity.getBody().init());
                } catch (Exception e) {
                    currentContext.setSendZuulResponse(false);
                    currentContext.setResponseBody("{\"success\":false,\"code\":40001, \"message\":\"refresh fail\"}");
                    currentContext.getResponse().setContentType("application/json");
                }
            }
            currentContext.addZuulRequestHeader("Authorization", "bearer " + tokenInfo.getAccess_token());
        }
        return null;
    }
}
