package cn.budingcc.framework.domain.ucenter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/1/30 19:24
 */
@Data
@ToString
@Entity
@Table(name="bd_role")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@JsonIgnoreProperties(value = "handler")
public class BdRole {
    
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    
    private String roleName;
    private String roleCode;
    private String description;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    
    
}