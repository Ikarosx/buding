package cn.budingcc.framework.domain.shop;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Ikaros
 * @date 2020/1/29 13:06
 */
@Data
@ToString
@Entity
@Table(name="bd_good_category")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class Category implements Serializable {
    private static final long serialVersionUID = -906357110051689484L;
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(length = 32)
    private String id;
    private String name;
    private String parentId;
    
    @Column(name = "is_show")
    private Boolean show;
    private Integer orderBy;
    @Column(name = "is_leaf")
    private Boolean leaf;
    
}
