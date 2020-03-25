package cn.budingcc.es.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/2/26 14:01
 */
@Data
@ToString
@Entity
@Document(indexName = "bd_good", type = "_doc")
public class EsGood implements Serializable {
    private static final long serialVersionUID = -906357110051689484L;
    
    @Id
    private String id;
    @Field(type = FieldType.Keyword)
    private String userName;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String goodName;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    private String description;
    @Field(type = FieldType.Text)
    private String imageUrl;
    @Field(type = FieldType.Float)
    private Double price;
    @Field(type = FieldType.Keyword)
    private String userId;
    @Field(type = FieldType.Keyword)
    private String schoolId;
    @Field(type = FieldType.Text)
    private String rootCategoryId;
    @Field(type = FieldType.Keyword)
    private String directCategoryId;
    @Field(type = FieldType.Text)
    private Long browseCount;
    @Field(type = FieldType.Text)
    private Long reportCount;
    @Field(type = FieldType.Keyword)
    private Integer state;
    @Field(type = FieldType.Date)
    private Date createTime;
    @Field(type = FieldType.Date)
    private Date updateTime;
}
