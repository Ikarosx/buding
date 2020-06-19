package cn.budingcc.socketio.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/6/12 12:05
 */
@Data
@Entity
@Table(name = "message")
public class MessageEntity {
    @Id
    private String id;
    private String message;
    private String fromUser;
    private String toUser;
    private boolean visited;
    private Date createTime;
}