package cn.budingcc.filesystem.service;

import cn.budingcc.framework.domain.filesystem.response.UploadFileResult;
import cn.budingcc.framework.model.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ikaros
 * @date 2020/2/20 14:19
 */
public interface FileSystemService {
    UploadFileResult upload(MultipartFile file, String businesskey, String fileName, String metadata);
    
    ResponseResult deleteFile(String id);
    
}
