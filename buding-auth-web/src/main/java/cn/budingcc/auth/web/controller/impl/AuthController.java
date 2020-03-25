package cn.budingcc.auth.web.controller.impl;

import cn.budingcc.auth.web.controller.AuthControllerApi;
import cn.budingcc.auth.web.domain.TokenInfo;
import cn.budingcc.auth.web.exception.AuthExceptionEnum;
import cn.budingcc.framework.exception.ExceptionCast;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Ikaros
 * @date 2020/3/18 9:44
 */
@RestController
@Slf4j
@RequestMapping("oauth")
public class AuthController implements AuthControllerApi {
    @Value("${buding.auth.serverUrl}")
    private String authServerUrl;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    @PostMapping("/logout")
    public ResponseResult logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    @GetMapping("/me")
    public TokenInfo me(HttpServletRequest request, HttpServletResponse response) {
        TokenInfo tokenInfo = (TokenInfo) request.getSession().getAttribute("tokenInfo");
        log.info("me tokeninfo is:" + tokenInfo);
        return tokenInfo;
    }
    
    @Override
    @GetMapping("/callback")
    public void callback(String code, String state, HttpServletRequest request, HttpServletResponse response) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBasicAuth("webBuding", "123456");
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "http://admin.budingcc.cn:40010/oauth/callback");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<TokenInfo> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(authServerUrl + "/oauth/token", HttpMethod.POST, httpEntity, TokenInfo.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionCast.cast(AuthExceptionEnum.USER_OR_PASSWORD_ERROR);
        }
        if (responseEntity.getBody() == null) {
            ExceptionCast.cast(AuthExceptionEnum.USER_OR_PASSWORD_ERROR);
        }
        TokenInfo tokenInfo = responseEntity.getBody();
        // 添加access_token
        Cookie budingAccessToken = new Cookie("buding_access_token", tokenInfo.getAccess_token());
        budingAccessToken.setMaxAge(tokenInfo.getExpires_in().intValue() - 3);
        budingAccessToken.setDomain("budingcc.cn");
        budingAccessToken.setPath("/");
        response.addCookie(budingAccessToken);
        // 添加refresh_token
        Cookie budingRefreshToken = new Cookie("buding_refresh_token", tokenInfo.getRefresh_token());
        budingAccessToken.setMaxAge(259200);
        budingAccessToken.setDomain("budingcc.cn");
        budingAccessToken.setPath("/");
        response.addCookie(budingRefreshToken);
        try {
            response.sendRedirect(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
