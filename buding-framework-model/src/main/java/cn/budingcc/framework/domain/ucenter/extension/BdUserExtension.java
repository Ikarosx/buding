package cn.budingcc.framework.domain.ucenter.extension;

import cn.budingcc.framework.domain.ucenter.BdMenu;
import cn.budingcc.framework.domain.ucenter.BdUser;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/1/30 18:58
 */
@Data
@ToString
public class BdUserExtension extends BdUser {
    private List<BdMenu> permissions;
}
