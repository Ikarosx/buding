package cn.budingcc.framework.domain.filesystem;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

/**
 * @author Ikaros
 * @date 2020/2/20 13:34
 */
@Data
@ToString
@Document(collection = "filesystem")
public class FileSystem {

    @Id
    private String fileId;
    private String filePath;
    private long fileSize;
    private String fileName;
    private String fileType;
    private int fileWidth;
    private int fileHeight;
    private String userId;
    private String businessKey;
    private Map metadata;
    
}
