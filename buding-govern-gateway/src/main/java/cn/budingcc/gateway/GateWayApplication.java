package cn.budingcc.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author Ikaros
 * @date 2020/3/16 16:47
 */
@SpringBootApplication
@EnableZuulProxy
public class GateWayApplication  {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }
}
