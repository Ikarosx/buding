package cn.budingcc.framework.domain.ucenter.request;

import cn.budingcc.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * @author Ikaros
 * @date 2020/2/12 16:19
 */
@Data
@ToString
public class SchoolListRequest extends RequestData {
    private String schoolName;
    private Integer systemType;
    private Integer state;
}
