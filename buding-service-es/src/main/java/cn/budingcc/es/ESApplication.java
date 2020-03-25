package cn.budingcc.es;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author Ikaros
 * @date 2020/2/23 20:11
 */
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
@EnableDiscoveryClient
@EntityScan(basePackages = "cn.budingcc.framework.domain")
@ComponentScan(basePackages = "cn.budingcc.api")
@ComponentScan(basePackages = "cn.budingcc.es")
@ComponentScan(basePackages = "cn.budingcc.framework")
@EnableElasticsearchRepositories(basePackages = "cn.budingcc.es.dao")
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ESApplication {
    public static void main(String[] args) {
        SpringApplication.run(ESApplication.class, args);
    }
}
