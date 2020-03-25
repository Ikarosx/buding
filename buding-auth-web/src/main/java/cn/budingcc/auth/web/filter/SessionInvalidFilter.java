package cn.budingcc.auth.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Ikaros
 * @date 2020/3/21 11:06
 */
@Component
@Slf4j
public class SessionInvalidFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }
    
    @Override
    public int filterOrder() {
        return 1;
    }
    
    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        if (request.getRequestURI().startsWith("/oauth")) {
            return false;
        }
        return true;
    }
    
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpSession session = currentContext.getRequest().getSession(false);
        if (session == null) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseBody("{\"success\":false,\"code\":40001, \"message\":\"session invalid\"}");
            currentContext.getResponse().setContentType("application/json");
        }
        return null;
    }
}
