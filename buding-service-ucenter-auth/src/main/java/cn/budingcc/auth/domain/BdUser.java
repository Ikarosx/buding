package cn.budingcc.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/1/27 19:36
 */
@Data
@ToString
@Entity
@Table(name = "bd_user")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@JsonIgnoreProperties(value = "handler")
public class BdUser implements UserDetails {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    
    private String studentId;
    private String userName;
    private String password;
    private String salt;
    private String schoolId;
    private String nickName;
    private String birthday;
    private String userPic;
    private Integer sex;
    private String email;
    private String phone;
    private Integer state;
    private String qq;
    private Date createTime;
    private Date updateTime;
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN");
    }
    
    @Override
    public String getUsername() {
        return userName;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
}
