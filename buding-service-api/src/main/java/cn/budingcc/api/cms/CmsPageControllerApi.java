package cn.budingcc.api.cms;

import cn.budingcc.framework.domain.cms.CmsPage;
import cn.budingcc.framework.domain.cms.request.ListCmsPageParam;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Ikaros
 * @date 2020/3/1 7:38
 */
@Api("页面接口")
public interface CmsPageControllerApi {
    
    @ApiOperation("添加页面")
    ResponseResult insertCmsPage(CmsPage cmsPage);
    
    @ApiOperation("删除页面")
    ResponseResult deleteCmsPage(String id);
    
    @ApiOperation("修改页面")
    ResponseResult updateCmsPage(CmsPage cmsPage, String id);
    
    @ApiOperation("查询页面")
    QueryResponseResult listCmsPages(int page, int size, ListCmsPageParam listCmsPageParam);
}
