package cn.budingcc.ucenter.dao;

import cn.budingcc.framework.domain.ucenter.BdMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/3/26 16:41
 */
public interface BdMenuRepository extends JpaRepository<BdMenu, String> {
    @Query(value = "SELECT id, code, p_id, menu_name , url, is_menu menu, level, sort, status, icon, create_time, update_time FROM bd_menu WHERE id IN(SELECT menu_id FROM bd_permission WHERE role_id IN(SELECT role_id FROM bd_user_role WHERE user_id = ?1))", nativeQuery = true)
    List<BdMenu> selectPermissionByUserId(String userId);
}
