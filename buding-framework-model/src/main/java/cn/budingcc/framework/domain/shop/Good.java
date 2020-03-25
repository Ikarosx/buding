package cn.budingcc.framework.domain.shop;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/1/28 15:41
 */
@Data
@ToString
@Entity
@Table(name = "bd_good")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Good implements Serializable {
    private static final long serialVersionUID = -906357110051689484L;
    
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    private String userName;
    private String goodName;
    private String description;
    private String imageUrl;
    private Double price;
    private String userId;
    private String schoolId;
    private String rootCategoryId;
    private String directCategoryId;
    private Long browseCount;
    private Long reportCount;
    private Integer state;
    private Date createTime;
    private Date updateTime;
}
