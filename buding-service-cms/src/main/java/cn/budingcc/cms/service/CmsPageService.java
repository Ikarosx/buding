package cn.budingcc.cms.service;

import cn.budingcc.framework.domain.cms.CmsPage;
import cn.budingcc.framework.domain.cms.request.ListCmsPageParam;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;

/**
 * @author Ikaros
 * @date 2020/3/1 10:44
 */
public interface CmsPageService {
    ResponseResult insertCmsPage(CmsPage cmsPage);
    
    ResponseResult deleteCmsPage(String id);
    
    ResponseResult updateCmsPage(CmsPage cmsPage, String id);
    
    QueryResponseResult listCmsPages(int page, int size, ListCmsPageParam listCmsPageParam);
}
