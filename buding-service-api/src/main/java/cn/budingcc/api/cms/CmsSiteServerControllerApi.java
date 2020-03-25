package cn.budingcc.api.cms;

import cn.budingcc.framework.domain.cms.CmsSiteServer;
import cn.budingcc.framework.domain.cms.request.ListCmsSiteServerParam;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Ikaros
 * @date 2020/3/1 7:38
 */
@Api("CMS网站服务器信息接口")
public interface CmsSiteServerControllerApi {
    
    @ApiOperation("添加CMS网站服务器")
    ResponseResult insertCmsSiteServer(CmsSiteServer cmsSiteServer);
    
    @ApiOperation("删除CMS网站服务器")
    ResponseResult deleteCmsSiteServer(String id);
    
    @ApiOperation("修改CMS网站服务器")
    ResponseResult updateCmsSiteServer(CmsSiteServer cmsSiteServer, String id);
    
    @ApiOperation("查询CMS网站服务器")
    QueryResponseResult listCmsSiteServers(int page, int size, ListCmsSiteServerParam listCmsSiteServerParam);
}
