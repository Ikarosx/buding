package cn.budingcc.framework.domain.ucenter;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/2/12 20:51
 */
@Data
@ToString
@Entity
@Table(name = "bd_school_system")
public class BdSchoolSystem implements Serializable {
    private static final long serialVersionUID = -906357110051689484L;
    
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;
    
    private String systemName;
    private Date createTime;
}
