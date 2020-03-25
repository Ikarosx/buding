package cn.budingcc.framework.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/3/1 7:40
 */
@Entity
@Data
@ToString
@Document("cms_site_template")
public class CmsSiteTemplate {
    @Id
    private String id;
    private String templateName;
    private String templateParameter;
    private String templateFileId;
    private Date createTime;
    private Date updateTime;
}
