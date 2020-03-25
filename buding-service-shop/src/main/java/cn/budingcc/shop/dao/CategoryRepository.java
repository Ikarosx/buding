package cn.budingcc.shop.dao;

import cn.budingcc.framework.domain.shop.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Ikaros
 * @date 2020/1/29 13:31
 */
public interface CategoryRepository extends JpaRepository<Category, String> {
    int countByParentId(String parentId);
}

