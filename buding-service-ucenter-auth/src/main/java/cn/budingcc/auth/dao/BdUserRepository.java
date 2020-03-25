package cn.budingcc.auth.dao;

import cn.budingcc.auth.domain.BdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ikaros
 * @date 2020/3/16 10:00
 */
public interface BdUserRepository extends JpaRepository<BdUser, String> {
    BdUser findByUserName(String userName);
}
