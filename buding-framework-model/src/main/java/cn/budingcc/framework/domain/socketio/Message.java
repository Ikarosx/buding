package cn.budingcc.framework.domain.socketio;

import lombok.Data;
import lombok.ToString;

/**
 * @author Ikaros
 * @date 2020/3/4 11:51
 */
@Data
@ToString
public class Message {
    String content;
    String username;
}
