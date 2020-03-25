package cn.budingcc.filesystem.util;

import cn.budingcc.framework.domain.filesystem.response.FileSystemCodeEnum;
import cn.budingcc.framework.exception.ExceptionCast;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Component
public class FileDfsUtil {
    @Resource
    private FastFileStorageClient storageClient;
    
    /**
     * 上传文件
     */
    public String upload(MultipartFile multipartFile) throws Exception {
        String originalFilename = multipartFile.getOriginalFilename().
                substring(multipartFile.getOriginalFilename().
                        lastIndexOf(".") + 1);
        StorePath storePath = this.storageClient.uploadImageAndCrtThumbImage(multipartFile.getInputStream(), multipartFile.getSize(), originalFilename, null);
        return storePath.getFullPath();
    }
    
    /**
     * 删除文件
     */
    public void deleteFile(String fileUrl) {
        
        try {
            StorePath storePath = StorePath.parseFromUrl(fileUrl);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            ExceptionCast.cast(FileSystemCodeEnum.FS_DELETE_FILE_ERROR);
        }
    }
}