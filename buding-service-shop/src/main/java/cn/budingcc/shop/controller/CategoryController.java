package cn.budingcc.shop.controller;

import cn.budingcc.api.shop.CategoryControllerApi;
import cn.budingcc.framework.domain.shop.Category;
import cn.budingcc.framework.exception.ExceptionCast;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.shop.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

/**
 * @author Ikaros
 * @date 2020/1/29 13:29
 */
@RestController
@RequestMapping("category")
public class CategoryController implements CategoryControllerApi {
    
    @Autowired
    private CategoryService categoryService;
    
    @Override
    @PostMapping
    public ResponseResult insertCategory(@RequestBody Category category) {
        categoryInsertValidate(category);
        return categoryService.insertCategory(category);
    }
    
    private void categoryInsertValidate(@RequestBody Category category) {
        Boolean leaf = category.getLeaf();
        if (leaf == null) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        Boolean show = category.getShow();
        if (show == null) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        String name = category.getName();
        String regex = "[\u4e00-\u9fa5a-zA-Z]{1,20}";
        if (StringUtils.isEmpty(name) || !Pattern.matches(regex, name)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        String parentId = category.getParentId();
        String idRegex = "[0-9-]+";
        if (StringUtils.isEmpty(parentId) || !Pattern.matches(idRegex, parentId)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        
    }
    
    @Override
    @DeleteMapping("/{categoryId}")
    public ResponseResult deleteCategory(@PathVariable String categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
    
    @Override
    @PutMapping("/{categoryId}")
    public ResponseResult updateCategory(@RequestBody Category category, @PathVariable String categoryId) {
        String id = category.getId();
        String parentId = category.getParentId();
        String idRegex = "[0-9-]+";
        if (!StringUtils.isEmpty(id) && !Pattern.matches(idRegex, id)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        if (!StringUtils.isEmpty(parentId) && !Pattern.matches(idRegex, parentId)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        String name = category.getName();
        String regex = "[\u4e00-\u9fa5a-zA-Z]{1,20}";
        if (!StringUtils.isEmpty(name) && !Pattern.matches(regex, name)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        return categoryService.updateCategory(category);
    }
    
    @Override
    @GetMapping("list")
    public QueryResponseResult listCategories(@RequestBody(required = false) Category category) {
        if (category == null) {
            category = new Category();
        }
        return categoryService.listCategories(category);
    }
    
    @Override
    @GetMapping("struct")
    public QueryResponseResult listCategoryStruct() {
        return categoryService.listCategoryStruct();
    }
}
