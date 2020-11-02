package cn.budingcc.gateway.service.impl;

import cn.budingcc.gateway.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

/**
 * @author Ikaros
 * @date 2020/3/26 7:44
 */
@Service
@Slf4j
public class PermissionServiceImpl implements PermissionService {
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        log.info(request.getRequestURI());
        log.info(String.valueOf(authentication));
        Random random = new Random();
        return (random.nextInt() & 1) == 0;
    }
}
