package cn.budingcc.socketio.handler;

import cn.budingcc.framework.domain.socketio.Message;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Ikaros
 * @date 2020/3/4 10:31
 */
@Component
public class MessageEventHandler {
    private SocketIOServer socketIoServer;
    
    @Autowired
    public MessageEventHandler(SocketIOServer socketIoServer) {
        this.socketIoServer = socketIoServer;
    }
    
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String mac = client.getHandshakeData().getSingleUrlParam("mac");
        HandshakeData handshakeData = client.getHandshakeData();
        System.out.println(handshakeData.getUrlParams());
        System.out.println("客户端:" + client.getSessionId() + "已连接,mac=" + mac);
    }
    
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        System.out.println("客户端:" + client.getSessionId() + "断开连接");
    }
    
    @OnEvent(value = "chat")
    public void onEvent(SocketIOClient client, AckRequest request, Message message) {
        System.out.println(message.getUserName() + "发来消息：" + message.getContent());
        //服务器端向该客户端发送消息
        client.sendEvent("receiveChat", "我是服务器，我收到了你的消息");
    }
    
}
