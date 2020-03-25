package cn.budingcc.api.shop;

import cn.budingcc.framework.domain.shop.Category;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Ikaros
 * @date 2020/1/29 13:22
 */
@Api(value = "分类接口")
public interface CategoryControllerApi {
    
    @ApiOperation(value = "添加分类")
    ResponseResult insertCategory(Category category);
    
    @ApiOperation(value = "删除分类")
    ResponseResult deleteCategory(String categoryId);
    
    @ApiOperation(value = "修改分类")
    ResponseResult updateCategory(Category category, String categoryId);
    
    @ApiOperation(value = "查询分类")
    QueryResponseResult listCategories(Category category);
    
    @ApiOperation(value = "列出分类结构")
    QueryResponseResult listCategoryStruct();
    
}
