package cn.budingcc.socketio.service;

public interface SocketIOService {
    
    //推送的事件
    public static final String SEND_MESSAGE = "send_message";
    public static final String RECEIVE_MESSAGE = "receive_message";
    
    void start() throws Exception;
    
    void stop();
    
}