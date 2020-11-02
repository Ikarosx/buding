package cn.budingcc.filesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

/**
 * @author Ikaros
 * @date 2020/2/20 10:11
 */
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("cn.budingcc.framework.domain.filesystem")
@ComponentScan(basePackages = {"cn.budingcc.api"})
@ComponentScan(basePackages = {"cn.budingcc.framework"})
@ComponentScan(basePackages = {"cn.budingcc.filesystem"})
public class FileSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(FileSystemApplication.class, args);
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
