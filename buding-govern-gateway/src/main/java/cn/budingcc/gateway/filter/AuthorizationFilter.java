package cn.budingcc.gateway.filter;

import cn.budingcc.gateway.domain.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Ikaros
 * @date 2020/3/17 8:29
 */
@Component
@Slf4j
public class AuthorizationFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }
    
    @Override
    public int filterOrder() {
        return 5;
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
        if (isNeedAuth(request)) {
            TokenInfo tokenInfo = (TokenInfo) request.getSession().getAttribute("tokenInfo");
            if (tokenInfo != null && tokenInfo.isActive()) {
                if (!hasPermission(tokenInfo, request)) {
                    log.info("audit log update fial 403");
                    handlerError(403, context);
                }
                context.addZuulRequestHeader("username", tokenInfo.getUser_name());
            } else {
                if (!StringUtils.startsWithIgnoreCase(request.getRequestURI(), "/api/oauth") && !StringUtils.equals(request.getRequestURI(), "/api/ucenter/school/list") && !StringUtils.equals(request.getRequestURI(), "/api/ucenter/oauth/me")) {
                    log.error("audit log update fial 401");
                    handlerError(401, context);
                }
            }
        }
        return null;
    }
    
    private void handlerError(int i, RequestContext currentContext) {
        currentContext.getResponse().setContentType("application/json");
        currentContext.setResponseStatusCode(i);
        currentContext.setResponseBody("{'message': 'auth fail'}");
        // 不要往下走了
        currentContext.setSendZuulResponse(false);
    }
    
    private boolean hasPermission(TokenInfo tokenInfo, HttpServletRequest request) {
        return true;
    }
    
    private boolean isNeedAuth(HttpServletRequest request) {
        return true;
    }
}
