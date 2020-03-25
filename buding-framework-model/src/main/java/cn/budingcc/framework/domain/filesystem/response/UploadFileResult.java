package cn.budingcc.framework.domain.filesystem.response;

import cn.budingcc.framework.domain.filesystem.FileSystem;
import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

/**
 * @author Ikaros
 * @date 2020/2/20 13:56
 */
@Data
@ToString
public class UploadFileResult extends ResponseResult {
    FileSystem fileSystem;
    public UploadFileResult(ResultCode resultCode, FileSystem fileSystem) {
        super(resultCode);
        this.fileSystem = fileSystem;
    }
}
