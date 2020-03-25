package cn.budingcc.ucenter.dao;

import cn.budingcc.framework.domain.ucenter.BdMenu;
import cn.budingcc.framework.domain.ucenter.BdUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/1/30 21:31
 */
public interface BdUserRepository extends JpaRepository<BdUser, String> {
    @Query(value = "SELECT id, code, p_id pId, menu_name menuName, url, is_menu isMenu, level, sort, status, icon, create_time createTime, update_time updateTime FROM bd_menu WHERE id IN(SELECT menu_id FROM bd_permission WHERE role_id IN(SELECT role_id FROM bd_user_role WHERE user_id = ?1))", nativeQuery = true)
    List<BdMenu> selectPermissionByUserId(String userId);
    
    BdUser findByUserName(String userName);
}
