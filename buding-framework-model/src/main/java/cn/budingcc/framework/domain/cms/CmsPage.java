package cn.budingcc.framework.domain.cms;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/3/1 7:40
 */
@Entity
@Data
@ToString
@Document("cms_page")
public class CmsPage implements Serializable {
    private static final long serialVersionUID = -906357110051689484L;
    
    @Id
    private String id;
    private String siteId;
    private String pageName;
    private String pageAlias;
    private String pageWebPath;
    private String pagePhysicalPath;
    private String dataUrl;
    private String htmlFileId;
    private Date createTime;
    private Date updateTime;
}
