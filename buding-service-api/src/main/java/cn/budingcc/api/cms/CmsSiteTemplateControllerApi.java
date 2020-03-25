package cn.budingcc.api.cms;

import cn.budingcc.framework.domain.cms.CmsSiteTemplate;
import cn.budingcc.framework.domain.cms.request.ListCmsSiteTemplateParam;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ikaros
 * @date 2020/3/1 7:38
 */
@Api("CMS网页模板接口")
public interface CmsSiteTemplateControllerApi {
    
    @ApiOperation("添加CMS网页模板文件")
    ResponseResult insertCmsSiteTemplateFile(MultipartFile file);
    
    @ApiOperation("添加CMS网页模板信息")
    ResponseResult insertCmsSiteTemplate(CmsSiteTemplate cmsSiteTemplate);
    
    @ApiOperation("删除CMS网页模板")
    ResponseResult deleteCmsSiteTemplate(String id);
    
    @ApiOperation("修改CMS网页模板")
    ResponseResult updateCmsSiteTemplate(CmsSiteTemplate cmsSiteTemplate, String id);
    
    @ApiOperation("查询CMS网页模板")
    QueryResponseResult listCmsSiteTemplates(int page, int size, ListCmsSiteTemplateParam listCmsSiteTemplateParam);
}
