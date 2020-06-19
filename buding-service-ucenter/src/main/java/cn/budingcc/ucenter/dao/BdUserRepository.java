package cn.budingcc.ucenter.dao;

import cn.budingcc.framework.domain.ucenter.BdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ikaros
 * @date 2020/1/30 21:31
 */
public interface BdUserRepository extends JpaRepository<BdUser, String> {

    
    BdUser findByUserName(String userName);
    
    BdUser findByStudentId(String studentId);
}
