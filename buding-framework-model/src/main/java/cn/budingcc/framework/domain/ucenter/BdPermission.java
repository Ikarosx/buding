package cn.budingcc.framework.domain.ucenter;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/1/30 19:22
 */
@Data
@ToString
@Entity
@Table(name = "bd_permission")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class BdPermission {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    
    private String roleId;
    private String menuId;
    private Date createTime;
    private Date updateTime;
}
