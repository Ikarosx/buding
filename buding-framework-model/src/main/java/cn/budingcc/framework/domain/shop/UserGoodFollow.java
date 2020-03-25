package cn.budingcc.framework.domain.shop;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/1/29 14:02
 */
@Data
@ToString
@Entity
@Table(name = "bd_user_good_follow")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class UserGoodFollow implements Serializable {
    private static final long serialVersionUID = -906357110051689484L;
    
    @Id
    @Column(length = 32)
    @GeneratedValue(generator = "jpa-uuid")
    private String id;
    
    private String userId;
    private String goodId;
    private Date createTime;
    private Date updateTime;
}
