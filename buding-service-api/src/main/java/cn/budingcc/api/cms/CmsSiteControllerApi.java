package cn.budingcc.api.cms;

import cn.budingcc.framework.domain.cms.CmsSite;
import cn.budingcc.framework.domain.cms.request.ListCmsSiteParam;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Ikaros
 * @date 2020/3/1 7:38
 */
@Api("CMS网页信息接口")
public interface CmsSiteControllerApi {
    
    @ApiOperation("添加CMS网页")
    ResponseResult insertCmsSite(CmsSite cmsSite);
    
    @ApiOperation("删除CMS网页")
    ResponseResult deleteCmsSite(String id);
    
    @ApiOperation("修改CMS网页")
    ResponseResult updateCmsSite(CmsSite cmsSite, String id);
    
    @ApiOperation("根据ID查询CMS页面")
    CmsSite getCmsSiteById(String id);
    
    @ApiOperation("查询CMS网页")
    QueryResponseResult listCmsSites(int page, int size, ListCmsSiteParam listCmsSiteParam);
}
