package cn.budingcc.filesystem.exception;

import cn.budingcc.framework.exception.ExceptionCatch;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author Ikaros
 * @date 2020/2/20 21:56
 */
@ControllerAdvice
public class CustomExceptionCatch extends ExceptionCatch {
    static {
        builder.put(MaxUploadSizeExceededException.class, CommonCodeEnum.FILE_SIZE_LIMIT_EXCEEDED);
    }
}
