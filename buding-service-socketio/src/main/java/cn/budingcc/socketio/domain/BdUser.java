package cn.budingcc.socketio.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * @author Ikaros
 * @date 2020/1/27 19:36
 */
@Data
public class BdUser extends User {
    private String id;
    
    private String student_id;
    private String user_name;
    private String school_id;
    private String nick_name;
    private String user_pic;
    
    public BdUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    
    
}
