package cn.budingcc.cms.controller;

import cn.budingcc.api.cms.CmsSiteControllerApi;
import cn.budingcc.cms.service.CmsSiteService;
import cn.budingcc.framework.domain.cms.CmsSite;
import cn.budingcc.framework.domain.cms.request.ListCmsSiteParam;
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
@RequestMapping("cms/site")
public class CmsSiteController implements CmsSiteControllerApi {
    @Autowired
    CmsSiteService cmsSiteService;
    
    @Override
    @PostMapping
    public ResponseResult insertCmsSite(@RequestBody CmsSite cmsSite) {
        cmsSite.setId(null);
        cmsSite.setCreateTime(new Date());
        cmsSite.setUpdateTime(cmsSite.getCreateTime());
        return cmsSiteService.insertCmsSite(cmsSite);
    }
    
    @Override
    @DeleteMapping("{id}")
    public ResponseResult deleteCmsSite(@PathVariable String id) {
        return cmsSiteService.deleteCmsSite(id);
    }
    
    @Override
    @PutMapping("{id}")
    public ResponseResult updateCmsSite(@RequestBody CmsSite cmsSite, @PathVariable String id) {
        return cmsSiteService.updateCmsSite(cmsSite, id);
    }
    
    @Override
    @GetMapping("{id}")
    public CmsSite getCmsSiteById(@PathVariable String id) {
        return cmsSiteService.getCmsSiteById(id);
    }
    
    @Override
    @GetMapping("list/{page}/{size}")
    public QueryResponseResult listCmsSites(@PathVariable int page, @PathVariable int size, ListCmsSiteParam listCmsSiteParam) {
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        return cmsSiteService.listCmsSites(page, size, listCmsSiteParam);
    }
}
