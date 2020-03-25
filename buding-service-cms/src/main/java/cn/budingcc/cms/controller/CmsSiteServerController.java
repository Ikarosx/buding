package cn.budingcc.cms.controller;

import cn.budingcc.api.cms.CmsSiteServerControllerApi;
import cn.budingcc.cms.service.CmsSiteServerService;
import cn.budingcc.framework.domain.cms.CmsSiteServer;
import cn.budingcc.framework.domain.cms.request.ListCmsSiteServerParam;
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
@RequestMapping("cms/site/server")
public class CmsSiteServerController implements CmsSiteServerControllerApi {
    @Autowired
    CmsSiteServerService cmsSiteServerService;
    
    @Override
    @PostMapping
    public ResponseResult insertCmsSiteServer(@RequestBody CmsSiteServer cmsSiteServer) {
        cmsSiteServer.setId(null);
        cmsSiteServer.setCreateTime(new Date());
        cmsSiteServer.setUpdateTime(cmsSiteServer.getCreateTime());
        return cmsSiteServerService.insertCmsSiteServer(cmsSiteServer);
    }
    
    @Override
    @DeleteMapping("{id}")
    public ResponseResult deleteCmsSiteServer(@PathVariable String id) {
        return cmsSiteServerService.deleteCmsSiteServer(id);
    }
    
    @Override
    @PutMapping("{id}")
    public ResponseResult updateCmsSiteServer(@RequestBody CmsSiteServer cmsSiteServer, @PathVariable String id) {
        return cmsSiteServerService.updateCmsSiteServer(cmsSiteServer, id);
    }
    
    @Override
    @GetMapping("list/{page}/{size}")
    public QueryResponseResult listCmsSiteServers(@PathVariable int page, @PathVariable int size, ListCmsSiteServerParam listCmsSiteServerParam) {
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        return cmsSiteServerService.listCmsSiteServers(page, size, listCmsSiteServerParam);
    }
}
