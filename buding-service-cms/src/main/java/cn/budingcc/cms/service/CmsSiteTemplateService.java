package cn.budingcc.cms.service;

import cn.budingcc.framework.domain.cms.CmsSiteTemplate;
import cn.budingcc.framework.domain.cms.request.ListCmsSiteTemplateParam;
import cn.budingcc.framework.domain.cms.response.TemplateUploadResult;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Ikaros
 * @date 2020/3/1 10:46
 */
public interface CmsSiteTemplateService {
    
    ResponseResult insertCmsSiteTemplate(CmsSiteTemplate cmsSiteTemplate);
    
    ResponseResult deleteCmsSiteTemplate(String id);
    
    ResponseResult updateCmsSiteTemplate(CmsSiteTemplate cmsSiteTemplate, String id);
    
    QueryResponseResult listCmsSiteTemplates(int page, int size, ListCmsSiteTemplateParam listCmsSiteTemplateParam);
    
    TemplateUploadResult insertCmsSiteTemplateFile(MultipartFile file);
}
