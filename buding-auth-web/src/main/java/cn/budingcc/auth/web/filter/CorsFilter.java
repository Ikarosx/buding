package cn.budingcc.auth.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ikaros
 * @date 2020/3/17 13:26
 */
@Component
@Slf4j
public class CorsFilter extends ZuulFilter {
    
    @Override
    public String filterType() {
        return "pre";
    }
    
    @Override
    public int filterOrder() {
        return 0;
    }
    
    @Override
    public boolean shouldFilter() {
        return true;
    }
    
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "authorization, content-type");
        response.setHeader("Access-Control-Allow-Method", "POST, GET, DELETE, PUT");
        response.setHeader("Access-Control-Expose-Headers", "x-forwared-port, x-forwared-host");
        response.setHeader("Vary", "Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            currentContext.setResponseStatusCode(200);
            currentContext.setSendZuulResponse(false);
        }
        return null;
    }
}
