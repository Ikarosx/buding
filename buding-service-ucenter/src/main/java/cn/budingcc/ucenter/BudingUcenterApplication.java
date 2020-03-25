package cn.budingcc.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Ikaros
 * @date 2020/1/30 18:43
 */
@SpringBootApplication
@MapperScan(basePackages = "cn.budingcc.ucenter.dao")
@ComponentScan(basePackages = "cn.budingcc.api")
@EntityScan(basePackages = "cn.budingcc.framework.domain.ucenter")
@ComponentScan(basePackages = "cn.budingcc.framework")
public class BudingUcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(BudingUcenterApplication.class, args);
    }
}
