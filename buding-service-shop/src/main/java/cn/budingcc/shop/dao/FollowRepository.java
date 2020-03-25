package cn.budingcc.shop.dao;

import cn.budingcc.framework.domain.shop.UserGoodFollow;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ikaros
 * @date 2020/1/29 14:13
 */
public interface FollowRepository extends JpaRepository<UserGoodFollow, String> {
    Integer deleteByUserIdAndGoodId(String userId, String goodId);
}
