package cn.budingcc.cms.service.impl;

import cn.budingcc.cms.dao.CmsSiteServerRepository;
import cn.budingcc.cms.service.CmsSiteServerService;
import cn.budingcc.framework.domain.cms.CmsSiteServer;
import cn.budingcc.framework.domain.cms.request.ListCmsSiteServerParam;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.QueryResult;
import cn.budingcc.framework.model.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author Ikaros
 * @date 2020/3/1 10:46
 */
@Service("CmsSiteServerService")
public class CmsSiteServerServiceImpl implements CmsSiteServerService {
    @Autowired
    CmsSiteServerRepository cmsSiteServerRepository;
    
    @Override
    public ResponseResult insertCmsSiteServer(CmsSiteServer cmsSiteServer) {
        cmsSiteServerRepository.save(cmsSiteServer);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult deleteCmsSiteServer(String id) {
        cmsSiteServerRepository.deleteById(id);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult updateCmsSiteServer(CmsSiteServer cmsSiteServer, String id) {
        cmsSiteServerRepository.save(cmsSiteServer);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public QueryResponseResult listCmsSiteServers(int page, int size, ListCmsSiteServerParam listCmsSiteServerParam) {
        CmsSiteServer cmsSiteServer = new CmsSiteServer();
        String serverName = listCmsSiteServerParam.getServerName();
        ExampleMatcher matcher = ExampleMatcher.matching();
        if (StringUtils.isNotEmpty(serverName)) {
            cmsSiteServer.setServerName(serverName);
            matcher.withMatcher("serverName", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        String serverPort = listCmsSiteServerParam.getServerPort();
        if (StringUtils.isNotEmpty(serverPort)) {
            cmsSiteServer.setServerPort(serverPort);
            matcher.withMatcher("serverPort", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        String serverDomain = listCmsSiteServerParam.getServerDomain();
        if (StringUtils.isNotEmpty(serverDomain)) {
            cmsSiteServer.setServerDomain(serverDomain);
            matcher.withMatcher("serverDomain", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Example<CmsSiteServer> example = Example.of(cmsSiteServer, matcher);
        Page<CmsSiteServer> cmsSiteServers = cmsSiteServerRepository.findAll(example, pageRequest);
        QueryResult<CmsSiteServer> queryResult = new QueryResult<>();
        queryResult.setList(cmsSiteServers.getContent());
        queryResult.setTotalPage(cmsSiteServers.getTotalPages());
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
}
