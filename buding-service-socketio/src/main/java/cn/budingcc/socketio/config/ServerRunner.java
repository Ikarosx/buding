package cn.budingcc.socketio.config;

import cn.budingcc.socketio.handler.MessageEventHandler;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 在项目服务启动的时候启动socket.io服务
 *
 * @author Ikaros
 * @date 2020/03/04 11:45
 */
@Component
public class ServerRunner implements CommandLineRunner {
    private final SocketIOServer server;
    private final MessageEventHandler messageEventHandler;
    
    @Autowired
    public ServerRunner(SocketIOServer server, MessageEventHandler messageEventHandler) {
        this.server = server;
        this.messageEventHandler = messageEventHandler;
    }
    
    @Override
    public void run(String... args) throws Exception {
        server.addListeners(messageEventHandler);
        server.start();
    }
}  