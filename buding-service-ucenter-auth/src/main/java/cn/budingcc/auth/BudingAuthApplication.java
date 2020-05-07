package cn.budingcc.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Ikaros
 * @date 2020/1/30 16:13
 */
@SpringBootApplication
@EnableFeignClients
public class BudingAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(BudingAuthApplication.class, args);
    }
}
