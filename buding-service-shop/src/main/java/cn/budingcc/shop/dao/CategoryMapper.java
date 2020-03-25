package cn.budingcc.shop.dao;

import cn.budingcc.framework.domain.shop.extension.CategoryNode;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/2/16 8:48
 */
@Mapper
public interface CategoryMapper {

    @Select("SELECT * FROM bd_good_category WHERE parent_id = 0")
    @Results(
            id = "category",
            value = {
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "id", column = "id"),
            @Result(property = "show", column = "is_show"),
            @Result(property = "leaf", column = "is_leaf"),
            @Result(property = "orderBy", column = "order_by"),
            @Result(property = "children", column = "id",many = @Many(select = "listCategoryStructByParentId"))
    })
    List<CategoryNode> listCategoryStruct();
    
    @Select("SELECT * FROM bd_good_category WHERE parent_id = #{parentId}")
    @ResultMap("category")
    List<CategoryNode> listCategoryStructByParentId(String parentId);
}
