package cn.budingcc.cms.service.impl;

import cn.budingcc.cms.dao.CmsPageRepository;
import cn.budingcc.cms.service.CmsPageService;
import cn.budingcc.framework.domain.cms.CmsPage;
import cn.budingcc.framework.domain.cms.request.ListCmsPageParam;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.QueryResult;
import cn.budingcc.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author Ikaros
 * @date 2020/3/1 10:46
 */
@Service("CmsPageService")
public class CmsPageServiceImpl implements CmsPageService {
    @Autowired
    CmsPageRepository cmsPageRepository;
    
    @Override
    public ResponseResult insertCmsPage(CmsPage cmsPage) {
        cmsPageRepository.save(cmsPage);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult deleteCmsPage(String id) {
        cmsPageRepository.deleteById(id);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult updateCmsPage(CmsPage cmsPage, String id) {
        cmsPageRepository.save(cmsPage);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public QueryResponseResult listCmsPages(int page, int size, ListCmsPageParam listCmsPageParam) {
        CmsPage cmsPage = new CmsPage();
        String pageAlias = listCmsPageParam.getPageAlias();
        if (StringUtils.isNotEmpty(pageAlias)) {
            cmsPage.setPageAlias(pageAlias);
        }
        String pageName = listCmsPageParam.getPageName();
        if (StringUtils.isNotEmpty(pageName)) {
            cmsPage.setPageName(pageName);
        }
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("pageAlias", ExampleMatcher.GenericPropertyMatchers.contains()).withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsPage> example = Example.of(cmsPage, matcher);
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(example, pageRequest);
        QueryResult<CmsPage> queryResult = new QueryResult<>();
        queryResult.setList(cmsPages.getContent());
        queryResult.setTotalPage(cmsPages.getTotalPages());
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
}
