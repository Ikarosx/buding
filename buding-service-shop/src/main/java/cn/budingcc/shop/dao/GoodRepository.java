package cn.budingcc.shop.dao;

import cn.budingcc.framework.domain.shop.Good;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ikaros
 * @date 2020/1/28 17:51
 */
public interface GoodRepository extends JpaRepository<Good, String> {
}
