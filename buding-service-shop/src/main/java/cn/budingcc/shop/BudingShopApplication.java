package cn.budingcc.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Ikaros
 * @date 2020/1/28 18:05
 */
@SpringBootApplication
@EnableFeignClients
@EntityScan(basePackages = "cn.budingcc.framework.domain")
@ComponentScan(basePackages = {"cn.budingcc.api"})
@ComponentScan(basePackages = {"cn.budingcc.framework"})
@ComponentScan(basePackages = {"cn.budingcc.shop"})
public class BudingShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(BudingShopApplication.class, args);
    }
}
