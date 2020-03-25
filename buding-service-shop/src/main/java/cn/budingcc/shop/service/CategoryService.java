package cn.budingcc.shop.service;

import cn.budingcc.framework.domain.shop.Category;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;

/**
 * @author Ikaros
 * @date 2020/1/29 13:30
 */
public interface CategoryService {
    ResponseResult insertCategory(Category category);
    
    ResponseResult deleteCategory(String categoryId);
    
    ResponseResult updateCategory(Category category);
    
    QueryResponseResult listCategories(Category category);
    
    QueryResponseResult listCategoryStruct();
}
