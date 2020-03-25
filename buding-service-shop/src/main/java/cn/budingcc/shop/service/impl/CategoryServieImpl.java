package cn.budingcc.shop.service.impl;

import cn.budingcc.framework.domain.shop.Category;
import cn.budingcc.framework.domain.shop.extension.CategoryNode;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.QueryResult;
import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.shop.dao.CategoryMapper;
import cn.budingcc.shop.dao.CategoryRepository;
import cn.budingcc.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/1/29 13:31
 */
@Service("CategoryService")
public class CategoryServieImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryMapper categoryMapper;
    
    @Override
    public ResponseResult insertCategory(Category category) {
        int count = categoryRepository.countByParentId(category.getParentId());
        category.setId(category.getParentId() + "-" + (count + 1));
        category.setOrderBy(count + 1);
        categoryRepository.save(category);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult deleteCategory(String categoryId) {
        categoryRepository.deleteById(categoryId);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult updateCategory(Category category) {
        categoryRepository.save(category);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public QueryResponseResult listCategories(Category category) {
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Category> example = Example.of(category, matcher);
        Sort sort = Sort.by("orderBy");
        List<Category> categoryList = categoryRepository.findAll(example, sort);
        QueryResult<Category> queryResult = new QueryResult<>();
        queryResult.setTotal(categoryList.size());
        queryResult.setList(categoryList);
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
    
    @Override
    public QueryResponseResult listCategoryStruct() {
        List<CategoryNode> categoryNodeList = categoryMapper.listCategoryStruct();
        QueryResult<CategoryNode> queryResult = new QueryResult<>();
        queryResult.setList(categoryNodeList);
        queryResult.setTotal(categoryNodeList.size());
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
}
