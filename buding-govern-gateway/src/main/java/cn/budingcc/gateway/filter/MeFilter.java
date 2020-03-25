package cn.budingcc.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ikaros
 * @date 2020/3/25 9:44
 */
@Component
public class MeFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }
    
    @Override
    public int filterOrder() {
        return 6;
    }
    
    @Override
    public boolean shouldFilter() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        return StringUtils.equals(request.getRequestURI(), "/ucenter/user/me");
    }
    
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String username = currentContext.getZuulRequestHeaders().get("username");
        if (StringUtils.isNotBlank(username)) {
            currentContext.setResponseBody("{\"username\":\"" + username + "\"}");
        }
        currentContext.setSendZuulResponse(false);
        currentContext.setResponseStatusCode(200);
        currentContext.getResponse().setContentType("application/json");
        return null;
    }
}
