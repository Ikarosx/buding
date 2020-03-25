package cn.budingcc.api.filesystem;

import cn.budingcc.framework.domain.filesystem.response.UploadFileResult;
import cn.budingcc.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ikaros
 * @date 2020/2/20 13:54
 */
@Api(value = "文件系统接口")
public interface FileSystemControllerApi {
    
    @ApiOperation(value = "上传文件")
    UploadFileResult upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "businessKey", required = false) String businessKey, @RequestParam(value = "fileName", required = false) String fileName, String metadata);
    
    @ApiOperation(value = "删除文件")
    ResponseResult deleteFile(String id);
}
