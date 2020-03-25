package cn.budingcc.framework.domain.ucenter;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/1/30 19:26
 */
@Data
@ToString
@Entity
@Table(name = "bd_school")
@DynamicUpdate
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class BdSchool {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    
    private String schoolName;
    private Integer state;
    private Integer systemType;
    private Date createTime;
    private Date updateTime;
}
