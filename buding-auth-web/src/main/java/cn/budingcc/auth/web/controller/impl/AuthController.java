package cn.budingcc.auth.web.controller.impl;

import cn.budingcc.auth.web.controller.AuthControllerApi;
import cn.budingcc.auth.web.domain.TokenInfo;
import cn.budingcc.auth.web.domain.response.JwtResponse;
import cn.budingcc.auth.web.exception.AuthExceptionEnum;
import cn.budingcc.auth.web.service.TokenService;
import cn.budingcc.auth.web.util.CookieUtil;
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
    private TokenService tokenService;
    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    @PostMapping("/logout")
    public ResponseResult logout(HttpServletRequest request) {
        request.getSession().invalidate();
        tokenService.deleteToken(tokenService.getJitFromCookie());
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    @GetMapping("/jwt")
    public JwtResponse jwt() {
        // 获取cookie中的jti
        String jti = tokenService.getJitFromCookie();
        // 根据jti从redis中查询jwt
        TokenInfo tokenInfo = tokenService.getTokenByJit(jti);
        if (tokenInfo == null) {
            return new JwtResponse(AuthExceptionEnum.GET_TOKEN_BY_JIT_FAIL, null);
        }
        return new JwtResponse(CommonCodeEnum.SUCCESS, tokenInfo.getAccess_token());
    }
    
    @Override
    @GetMapping("/callback")
    public void callback(String code, String state, HttpServletRequest request, HttpServletResponse response) {
        // 构造请求头和请求体获取令牌
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
            ExceptionCast.cast(AuthExceptionEnum.USER_OR_PASSWORD_ERROR);
        }
        if (responseEntity.getBody() == null) {
            ExceptionCast.cast(AuthExceptionEnum.USER_OR_PASSWORD_ERROR);
        }
        // 得到令牌
        TokenInfo tokenInfo = responseEntity.getBody();
        // 保存令牌到redis
        boolean saveTokenSuccess = tokenService.saveTokenToRedis(tokenInfo);
        if (!saveTokenSuccess) {
            ExceptionCast.cast(AuthExceptionEnum.SAVE_TOKEN_TO_REDIS_FAIL);
        }
        // 添加cookie到response
        CookieUtil.addCookie(response, "budingcc.cn", "/", "jti", tokenInfo.getJti(), 3600, true);
        try {
            response.sendRedirect(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
