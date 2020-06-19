package cn.budingcc.socketio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Ikaros
 * @date 2020/3/4 9:29
 */
@SpringBootApplication
@EntityScan("cn.budingcc.socketio.domain")
@ComponentScan(basePackages = "cn.budingcc.framework")
@ComponentScan(basePackages = "cn.budingcc.socketio")
public class SocketIOApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocketIOApplication.class, args);
    }
}
