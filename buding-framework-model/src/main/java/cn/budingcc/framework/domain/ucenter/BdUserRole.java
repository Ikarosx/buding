package cn.budingcc.framework.domain.ucenter;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/1/30 19:30
 */
@Data
@ToString
@Entity
@Table(name = "bd_user_role")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class BdUserRole {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    
    private String userId;
    private String roleId;
    private Date createTime;
    private Date updateTime;
    private String creator;
}
