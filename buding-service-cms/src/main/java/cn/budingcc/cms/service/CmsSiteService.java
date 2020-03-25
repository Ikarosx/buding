package cn.budingcc.cms.service;

import cn.budingcc.framework.domain.cms.CmsSite;
import cn.budingcc.framework.domain.cms.request.ListCmsSiteParam;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;

/**
 * @author Ikaros
 * @date 2020/3/1 10:45
 */
public interface CmsSiteService {
    
    ResponseResult insertCmsSite(CmsSite cmsSite);
    
    ResponseResult deleteCmsSite(String id);
    
    ResponseResult updateCmsSite(CmsSite cmsSite, String id);
    
    QueryResponseResult listCmsSites(int page, int size, ListCmsSiteParam listCmsSiteParam);
    
    CmsSite getCmsSiteById(String id);
}
