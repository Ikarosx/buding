package cn.budingcc.socketio.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private String id;
    private String message;
    private String fromUser;
    private String toUser;
    private Date createTime;
    private boolean visited;
}