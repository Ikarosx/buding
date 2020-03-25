package cn.budingcc.framework.domain.filesystem.response;

import cn.budingcc.framework.model.response.ResultCode;

/**
 * @author Ikaros
 * @date 2020/2/20 14:45
 */
public enum FileSystemCodeEnum implements ResultCode {
    /**
     * 文件系统相关错误代码 23000
     */
    FS_INITF_DFS_ERROR(false, 23001, "初始DFS化失败"),
    FS_DELETE_FILE_ERROR(false, 23002, "删除文件失败"),
    FS_UPLOAD_FILE_ERROR(false, 23003, "上传文件失败");
    
    private boolean success;
    private int code;
    private String message;
    
    FileSystemCodeEnum(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
    
    @Override
    public boolean success() {
        return success;
    }
    
    @Override
    public int code() {
        return code;
    }
    
    @Override
    public String message() {
        return message;
    }
}
