package cn.budingcc.auth.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author Ikaros
 * @date 2020/3/19 13:10
 */
@SpringBootApplication
@EnableZuulProxy
public class AuthWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthWebApplication.class, args);
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
