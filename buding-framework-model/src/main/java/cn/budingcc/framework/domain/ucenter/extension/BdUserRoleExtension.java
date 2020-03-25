package cn.budingcc.framework.domain.ucenter.extension;

import cn.budingcc.framework.domain.ucenter.BdRole;
import cn.budingcc.framework.domain.ucenter.BdUser;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/2/11 12:28
 */
@Data
@ToString(callSuper = true)
public class BdUserRoleExtension extends BdUser {
    private List<BdRole> bdRoleList;
    private String schoolName;
}
