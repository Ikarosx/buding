package cn.budingcc.socketio.service;

import cn.budingcc.socketio.domain.Message;
import cn.budingcc.socketio.domain.MessageEntity;
import cn.budingcc.socketio.repository.MessageRepository;
import com.alibaba.fastjson.JSON;
import com.corundumstudio.socketio.*;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SocketHandler
 *
 * @author wliduo[i@dolyw.com]
 * @date 2019/4/17 13:42
 */
@Component
@Slf4j
public class SocketHandler {
    private final String SEND_MESSAGE = "send_message";
    private final String RECEIVE_MESSAGE = "receive_message";
    private final String GET_UNREAD = "get_unread";
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Value("${security.oauth2.publicKey}")
    private String publicKey;
    /**
     * ConcurrentHashMap保存当前SocketServer用户ID对应关系
     */
    private static Map<String, UUID> clientMap = new ConcurrentHashMap<>();
    
    public Map<String, UUID> getClientMap() {
        return clientMap;
    }
    
    /**
     * socketIOServer
     */
    private final SocketIOServer socketIOServer;
    
    @Autowired
    public SocketHandler(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
    }
    
    @OnConnect
    public void onConnect(SocketIOClient client) {
        List<String> accessToken = client.getHandshakeData().getUrlParams().get("access_token");
        if (accessToken == null || accessToken.size() == 0) {
            log.debug("accessToken为null，拒绝连接");
            client.disconnect();
            return;
        }
        String authorization = accessToken.get(0);
        log.debug(authorization);
        Jwt jwt;
        String claims;
        try {
            jwt = JwtHelper.decodeAndVerify(authorization, new RsaVerifier(publicKey));
            claims = jwt.getClaims();
        } catch (Exception e) {
            e.printStackTrace();
            log.debug("{}不是有效的jwt", authorization);
            client.disconnect();
            return;
        }
        Map map = JSON.parseObject(claims, Map.class);
        log.debug("{}加入聊天了", map.get("student_id"));
        clientMap.put((String) map.get("student_id"), client.getSessionId());
        // 获取未读信息返回
        MessageEntity message = new MessageEntity();
        message.setVisited(false);
        message.setToUser((String) map.get("student_id"));
        List<MessageEntity> unreadMessage = messageRepository.findAll(Example.of(message));
        client.sendEvent(GET_UNREAD, new AckCallback<String>(String.class) {
            @Override
            public void onSuccess(String result) {
                log.debug("{}拉取消息成功", map.get("student_id"));
                messageRepository.deleteInBatch(unreadMessage);
            }
        }, unreadMessage);
        // HACK 回调函数不生效，这里可能存在前端没有接收到，但已经删除了数据
        messageRepository.deleteInBatch(unreadMessage);
    }
    
    @OnDisconnect
    public void onDisConnect(SocketIOClient client) {
        client.disconnect();
        for (Map.Entry<String, UUID> entry : clientMap.entrySet()) {
            if (entry.getValue() == client.getSessionId()) {
                System.out.println(entry.getKey() + "下线了");
                clientMap.remove(entry.getKey());
                break;
            }
        }
    }
    
    @OnEvent(SEND_MESSAGE)
    public void sendMessage(SocketIOClient client, AckRequest ackRequest, Message data) {
        System.out.println("收到来自" + data.getFromUser() + "发给" + data.getToUser() + "的消息：" + data.getMessage());
        data.setCreateTime(new Date());
        // ack的内容
        Message message = new Message();
        BeanUtils.copyProperties(data, message);
        message.setMessage(null);
        UUID uuid = clientMap.get(data.getToUser());
        SocketIOClient toClient = null;
        if (uuid != null) {
            toClient = socketIOServer.getClient(uuid);
        }
        if (toClient == null || !toClient.isChannelOpen()) {
            System.out.println(data.getToUser() + "失效");
            MessageEntity messageEntity = new MessageEntity();
            BeanUtils.copyProperties(data, messageEntity);
            messageEntity.setVisited(false);
            messageRepository.save(messageEntity);
            ackRequest.sendAckData(message);
            return;
        }
        
        ackRequest.sendAckData(message);
        toClient.sendEvent(RECEIVE_MESSAGE, new VoidAckCallback() {
            @Override
            protected void onSuccess() {
                System.out.println("服务端发送RECEIVE_MESSAGE成功");
            }
        }, data);
    }
    
}
