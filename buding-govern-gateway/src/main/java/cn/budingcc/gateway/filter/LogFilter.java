package cn.budingcc.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Ikaros
 * @date 2020/3/17 8:29
 */
@Component
@Slf4j
public class LogFilter extends ZuulFilter {
    
    
    @Override
    public String filterType() {
        return "pre";
    }
    
    @Override
    public int filterOrder() {
        return 4;
    }
    
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.sendZuulResponse();
    }
    
    @Override
    public Object run() throws ZuulException {
        // TODO
        log.info("log insert");
        return null;
    }
}
