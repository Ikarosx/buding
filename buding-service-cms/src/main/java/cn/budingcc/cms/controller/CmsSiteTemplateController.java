package cn.budingcc.cms.controller;

import cn.budingcc.api.cms.CmsSiteTemplateControllerApi;
import cn.budingcc.cms.service.CmsSiteTemplateService;
import cn.budingcc.framework.domain.cms.CmsSiteTemplate;
import cn.budingcc.framework.domain.cms.request.ListCmsSiteTemplateParam;
import cn.budingcc.framework.domain.cms.response.TemplateUploadResult;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/3/1 10:35
 */
@RestController
@RequestMapping("cms/site/template")
public class CmsSiteTemplateController implements CmsSiteTemplateControllerApi {
    @Autowired
    CmsSiteTemplateService cmsSiteTemplateService;
    
    
    @Override
    @PostMapping("file")
    public TemplateUploadResult insertCmsSiteTemplateFile(MultipartFile file) {
        return cmsSiteTemplateService.insertCmsSiteTemplateFile(file);
    }
    
    @Override
    @PostMapping
    public ResponseResult insertCmsSiteTemplate(@RequestBody CmsSiteTemplate cmsSiteTemplate) {
        cmsSiteTemplate.setId(null);
        cmsSiteTemplate.setCreateTime(new Date());
        cmsSiteTemplate.setUpdateTime(cmsSiteTemplate.getCreateTime());
        return cmsSiteTemplateService.insertCmsSiteTemplate(cmsSiteTemplate);
    }
    
    @Override
    @DeleteMapping("{id}")
    public ResponseResult deleteCmsSiteTemplate(@PathVariable String id) {
        return cmsSiteTemplateService.deleteCmsSiteTemplate(id);
    }
    
    @Override
    @PutMapping("{id}")
    public ResponseResult updateCmsSiteTemplate(@RequestBody CmsSiteTemplate cmsSiteTemplate, @PathVariable String id) {
        return cmsSiteTemplateService.updateCmsSiteTemplate(cmsSiteTemplate, id);
    }
    
    @Override
    @GetMapping("list/{page}/{size}")
    public QueryResponseResult listCmsSiteTemplates(@PathVariable int page, @PathVariable int size, ListCmsSiteTemplateParam listCmsSiteTemplateParam) {
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        return cmsSiteTemplateService.listCmsSiteTemplates(page, size, listCmsSiteTemplateParam);
    }
}
