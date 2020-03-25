package cn.budingcc.cms.service.impl;

import cn.budingcc.cms.dao.CmsSiteTemplateRepository;
import cn.budingcc.cms.service.CmsSiteTemplateService;
import cn.budingcc.framework.domain.cms.CmsSiteTemplate;
import cn.budingcc.framework.domain.cms.request.ListCmsSiteTemplateParam;
import cn.budingcc.framework.domain.cms.response.TemplateUploadResult;
import cn.budingcc.framework.exception.ExceptionCast;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.QueryResult;
import cn.budingcc.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ikaros
 * @date 2020/3/1 10:46
 */
@Service("CmsSiteTemplateService")
public class CmsSiteTemplateServiceImpl implements CmsSiteTemplateService {
    @Autowired
    CmsSiteTemplateRepository cmsSiteTemplateRepository;
    @Autowired
    GridFsTemplate gridFsTemplate;
    
    @Override
    public ResponseResult insertCmsSiteTemplate(CmsSiteTemplate cmsSiteTemplate) {
        cmsSiteTemplateRepository.save(cmsSiteTemplate);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult deleteCmsSiteTemplate(String id) {
        cmsSiteTemplateRepository.deleteById(id);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult updateCmsSiteTemplate(CmsSiteTemplate cmsSiteTemplate, String id) {
        cmsSiteTemplateRepository.save(cmsSiteTemplate);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public QueryResponseResult listCmsSiteTemplates(int page, int size, ListCmsSiteTemplateParam listCmsSiteTemplateParam) {
        CmsSiteTemplate cmsSiteTemplate = new CmsSiteTemplate();
        String templateName = listCmsSiteTemplateParam.getTemplateName();
        ExampleMatcher matcher = ExampleMatcher.matching();
        if (StringUtils.isNotEmpty(templateName)) {
            cmsSiteTemplate.setTemplateName(templateName);
            matcher.withMatcher("templateName", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Example<CmsSiteTemplate> example = Example.of(cmsSiteTemplate, matcher);
        Page<CmsSiteTemplate> cmsSites = cmsSiteTemplateRepository.findAll(example, pageRequest);
        QueryResult<CmsSiteTemplate> queryResult = new QueryResult<>();
        queryResult.setList(cmsSites.getContent());
        queryResult.setTotalPage(cmsSites.getTotalPages());
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
    
    @Override
    public TemplateUploadResult insertCmsSiteTemplateFile(MultipartFile file) {
        ObjectId id = null;
        try (InputStream inputStream = file.getInputStream()) {
            id = gridFsTemplate.store(inputStream, file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            ExceptionCast.cast(CommonCodeEnum.IO_EXCEPTION);
        }
        return new TemplateUploadResult(CommonCodeEnum.SUCCESS, id.toString());
    }
}
