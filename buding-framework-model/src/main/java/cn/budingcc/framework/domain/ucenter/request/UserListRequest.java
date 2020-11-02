package cn.budingcc.framework.domain.ucenter.request;

import cn.budingcc.framework.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 * @author Ikaros
 * @date 2020/2/11 11:03
 */
@Data
@ToString
public class UserListRequest extends RequestData {
    private String studentId;
    private String username;
    private Integer sex;
    private Integer roleId;
    private Integer state;
}
