package cn.budingcc.es.config;

import cn.budingcc.framework.exception.ExceptionCast;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ikarosx
 * @date 2020/11/4 21:38
 */
@Slf4j
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.debug("拒绝访问," + accessDeniedException.getMessage(), accessDeniedException);
        ExceptionCast.cast(CommonCodeEnum.ACCESS_DENIED);
    }
}
