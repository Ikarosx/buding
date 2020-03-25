package cn.budingcc.filesystem.service.impl;

import cn.budingcc.filesystem.dao.FileSystemRepository;
import cn.budingcc.filesystem.service.FileSystemService;
import cn.budingcc.filesystem.util.FileDfsUtil;
import cn.budingcc.framework.domain.filesystem.FileSystem;
import cn.budingcc.framework.domain.filesystem.response.FileSystemCodeEnum;
import cn.budingcc.framework.domain.filesystem.response.UploadFileResult;
import cn.budingcc.framework.exception.ExceptionCast;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.ResponseResult;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author Ikaros
 * @date 2020/2/20 14:20
 */
@Service("FileSystemService")
public class FileSystemServiceImpl implements FileSystemService {
    @Autowired
    FileSystemRepository fileSystemRepository;
    @Resource
    FileDfsUtil fileDfsUtil;
    
    @Override
    public UploadFileResult upload(MultipartFile file, String businessKey, String fileName, String metadata) {
        
        // 上传文件到fdfs
        String fileId = null;
        try {
            fileId = fileDfsUtil.upload(file);
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionCast.cast(FileSystemCodeEnum.FS_UPLOAD_FILE_ERROR);
        }
        if (StringUtils.isEmpty(fileId)) {
            ExceptionCast.cast(FileSystemCodeEnum.FS_UPLOAD_FILE_ERROR);
        }
        // 创建文件信息对象
        FileSystem fileSystem = new FileSystem();
        // 文件id
        fileSystem.setFileId(fileId);
        // 文件在文件系统中的路径
        fileSystem.setFilePath(fileId);
        // 业务标识
        fileSystem.setBusinessKey(businessKey);
        // 元数据
        if (StringUtils.isNotEmpty(metadata)) {
            try {
                Map map = JSON.parseObject(metadata, Map.class);
                fileSystem.setMetadata(map);
            } catch (Exception e) {
                e.printStackTrace();
                ExceptionCast.cast(CommonCodeEnum.JSON_PARSE_ERROR0);
            }
        }
        if (StringUtils.isEmpty(fileName)) {
            fileName = file.getOriginalFilename();
        }
        // 名称
        fileSystem.setFileName(fileName);
        // 大小
        fileSystem.setFileSize(file.getSize());
        // 文件类型
        fileSystem.setFileType(file.getContentType());
        fileSystemRepository.save(fileSystem);
        return new UploadFileResult(CommonCodeEnum.SUCCESS, fileSystem);
    }
    
    @Override
    public ResponseResult deleteFile(String id) {
        fileDfsUtil.deleteFile(id);
        fileSystemRepository.deleteById(id);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    
}
