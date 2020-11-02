package cn.budingcc.filesystem.controller;

import cn.budingcc.api.filesystem.FileSystemControllerApi;
import cn.budingcc.filesystem.service.FileSystemService;
import cn.budingcc.framework.domain.filesystem.response.FileSystemCodeEnum;
import cn.budingcc.framework.domain.filesystem.response.UploadFileResult;
import cn.budingcc.framework.exception.ExceptionCast;
import cn.budingcc.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ikaros
 * @date 2020/2/20 13:59
 */
@RestController
@RequestMapping("filesystemss")
public class FileSystemController implements FileSystemControllerApi {
    @Autowired
    FileSystemService fileSystemService;
    
    @Override
    @PostMapping
    public UploadFileResult upload(MultipartFile file, String businessKey, String fileName, String metadata) {
        if (file == null) {
            ExceptionCast.cast(FileSystemCodeEnum.FS_UPLOAD_FILE_ERROR);
        }
        return fileSystemService.upload(file, businessKey, fileName, metadata);
    }
    
    @Override
    @DeleteMapping
    public ResponseResult deleteFile(String id) {
        if (StringUtils.isEmpty(id)) {
            ExceptionCast.cast(FileSystemCodeEnum.FS_DELETE_FILE_ERROR);
        }
        return fileSystemService.deleteFile(id);
    }
}
