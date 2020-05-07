package cn.budingcc.gateway.config;

import cn.budingcc.gateway.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

/**
 * @author Ikaros
 * @date 2020/3/26 7:47
 */
@Component
public class GateWayWebSecurityExpressionHandler extends OAuth2WebSecurityExpressionHandler {
    @Autowired
    private PermissionService permissionService;
    
    @Override
    protected StandardEvaluationContext createEvaluationContextInternal(Authentication authentication, FilterInvocation invocation) {
        StandardEvaluationContext sec = super.createEvaluationContextInternal(authentication, invocation);
        sec.setVariable("permissionService", permissionService);
        return sec;
    }
    
}
