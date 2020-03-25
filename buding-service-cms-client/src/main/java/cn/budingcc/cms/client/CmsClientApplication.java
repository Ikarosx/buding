package cn.budingcc.cms.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Ikaros
 * @date 2020/3/3 12:11
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EntityScan(basePackages = "cn.budingcc.framework.domain")
@ComponentScan(basePackages = "cn.budingcc.api")
@ComponentScan(basePackages = "cn.budingcc.framework")
@ComponentScan(basePackages = "cn.budingcc.cms.client")
public class CmsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsClientApplication.class, args);
    }
}
