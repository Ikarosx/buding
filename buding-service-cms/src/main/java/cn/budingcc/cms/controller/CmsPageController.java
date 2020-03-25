package cn.budingcc.cms.controller;

import cn.budingcc.api.cms.CmsPageControllerApi;
import cn.budingcc.cms.service.CmsPageService;
import cn.budingcc.framework.domain.cms.CmsPage;
import cn.budingcc.framework.domain.cms.request.ListCmsPageParam;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author Ikaros
 * @date 2020/3/1 10:35
 */
@RestController
@RequestMapping("cms/page")
public class CmsPageController implements CmsPageControllerApi {
    @Autowired
    CmsPageService cmsPageService;
    
    @Override
    @PostMapping
    public ResponseResult insertCmsPage(@RequestBody CmsPage cmsPage) {
        cmsPage.setId(null);
        cmsPage.setCreateTime(new Date());
        cmsPage.setUpdateTime(cmsPage.getCreateTime());
        return cmsPageService.insertCmsPage(cmsPage);
    }
    
    @Override
    @DeleteMapping("{id}")
    public ResponseResult deleteCmsPage(@PathVariable String id) {
        return cmsPageService.deleteCmsPage(id);
    }
    
    @Override
    @PutMapping("{id}")
    public ResponseResult updateCmsPage(@RequestBody CmsPage cmsPage, @PathVariable String id) {
        return cmsPageService.updateCmsPage(cmsPage, id);
    }
    
    @Override
    @GetMapping("list/{page}/{size}")
    public QueryResponseResult listCmsPages(@PathVariable int page, @PathVariable int size, ListCmsPageParam listCmsPageParam) {
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        return cmsPageService.listCmsPages(page, size, listCmsPageParam);
    }
}
