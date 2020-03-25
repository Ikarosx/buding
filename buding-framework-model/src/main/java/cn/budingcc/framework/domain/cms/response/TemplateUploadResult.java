package cn.budingcc.framework.domain.cms.response;

import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.framework.model.response.ResultCode;
import lombok.Data;
import lombok.ToString;

/**
 * @author Ikaros
 * @date 2020/3/1 11:53
 */
@Data
@ToString
public class TemplateUploadResult extends ResponseResult {
    private String id;
    
    public TemplateUploadResult(ResultCode resultCode, String id) {
        super(resultCode);
        this.id = id;
    }
}
