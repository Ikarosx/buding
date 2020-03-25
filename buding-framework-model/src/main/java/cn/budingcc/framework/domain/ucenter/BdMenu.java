package cn.budingcc.framework.domain.ucenter;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/1/30 18:59
 */
@Data
@ToString
@Entity
@Table(name = "bd_menu")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class BdMenu {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    
    private String code;
    private String pId;
    private String menuName;
    private String url;
    private Boolean menu;
    private Integer level;
    private Integer sort;
    private Integer status;
    private String icon;
    private Date createTime;
    private Date updateTime;
    
}
