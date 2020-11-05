package cn.budingcc.gateway.config;

import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.gateway.exception.AuthExceptionEnum;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ikarosx
 * @date 2020/11/5 9:59
 */
@Slf4j
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(JSON.toJSONString(new ResponseResult(AuthExceptionEnum.LOGIN_RETRY)));
        log.debug("Auth异常,{}", authException.getMessage(), authException);
    }
}
