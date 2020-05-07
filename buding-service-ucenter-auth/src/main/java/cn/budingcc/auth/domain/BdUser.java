package cn.budingcc.auth.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author Ikaros
 * @date 2020/1/27 19:36
 */
@Data
public class BdUser extends User {
    private String id;
    
    private String studentId;
    private String userName;
    private String schoolId;
    private String nickName;
    private String userPic;
    
    public BdUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
