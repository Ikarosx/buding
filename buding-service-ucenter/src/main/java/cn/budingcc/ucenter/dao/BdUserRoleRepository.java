package cn.budingcc.ucenter.dao;

import cn.budingcc.framework.domain.ucenter.BdUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ikaros
 * @date 2020/1/31 16:51
 */
public interface BdUserRoleRepository extends JpaRepository<BdUserRole, String> {
    void deleteByUserId(String userId);
}
