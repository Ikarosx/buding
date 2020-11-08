package cn.budingcc.filesystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author Ikaros
 * @date 2020/6/7 18:32
 */
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    
    @Autowired
    private CustomAccessTokenConverter customAccessTokenConverter;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("esService").tokenServices(resourceServerTokenServices()).accessDeniedHandler(myAccessDeniedHandler);
    }
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();
    }
    
    public ResourceServerTokenServices resourceServerTokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }
    
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
    
    
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        jwtAccessTokenConverter.setAccessTokenConverter(customAccessTokenConverter);
        return jwtAccessTokenConverter;
    }
}
