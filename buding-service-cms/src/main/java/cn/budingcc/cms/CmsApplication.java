package cn.budingcc.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Ikaros
 * @date 2020/3/1 7:29
 */
@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = "cn.budingcc.framework.domain")
@ComponentScan(basePackages = "cn.budingcc.api.cms")
@ComponentScan(basePackages = "cn.budingcc.api.config")
@ComponentScan(basePackages = "cn.budingcc.framework")
@ComponentScan(basePackages = "cn.budingcc.cms")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
