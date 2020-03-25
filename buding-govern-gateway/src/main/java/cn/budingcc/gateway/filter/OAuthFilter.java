package cn.budingcc.gateway.filter;

import cn.budingcc.gateway.domain.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ikaros
 * @date 2020/3/17 8:29
 */
@Component
@Slf4j
public class OAuthFilter extends ZuulFilter {
    private RestTemplate restTemplate = new RestTemplate();
    
    @Override
    public String filterType() {
        return "pre";
    }
    
    @Override
    public int filterOrder() {
        return 3;
    }
    
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.sendZuulResponse();
    }
    
    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        if (StringUtils.startsWith(request.getRequestURI(), "/token")) {
            return null;
        }
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isBlank(authorization)) {
            log.error(request.getRequestURI() + " has null authorization header.");
            return null;
        }
        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer ")) {
            return null;
        }
        try {
            TokenInfo info = getTokenInfo(authorization);
            request.getSession().setAttribute("tokenInfo", info);
            log.info(request.getRequestURI() + "has authorization.");
        } catch (Exception e) {
            request.getSession().setAttribute("tokenInfo", null);
            log.error("get token info fail", e);
        }
        return null;
    }
    
    private TokenInfo getTokenInfo(String authorization) {
        String token = StringUtils.substringAfter(authorization, "bearer ");
        String oauthServiceUrl = "http://budingcc.cn:40000/oauth/check_token";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBasicAuth("webBuding", "123456");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", token);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<TokenInfo> responseEntity = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, entity, TokenInfo.class);
        return responseEntity.getBody();
    }
}
