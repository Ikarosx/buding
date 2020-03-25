package cn.budingcc.cms.service;

import cn.budingcc.framework.domain.cms.CmsSiteServer;
import cn.budingcc.framework.domain.cms.request.ListCmsSiteServerParam;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;

/**
 * @author Ikaros
 * @date 2020/3/1 10:45
 */
public interface CmsSiteServerService {
    ResponseResult insertCmsSiteServer(CmsSiteServer cmsSiteServer);
    
    ResponseResult deleteCmsSiteServer(String id);
    
    ResponseResult updateCmsSiteServer(CmsSiteServer cmsSiteServer, String id);
    
    QueryResponseResult listCmsSiteServers(int page, int size, ListCmsSiteServerParam listCmsSiteServerParam);
    
}
