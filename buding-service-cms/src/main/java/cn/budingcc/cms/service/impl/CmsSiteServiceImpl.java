package cn.budingcc.cms.service.impl;

import cn.budingcc.cms.dao.CmsSiteRepository;
import cn.budingcc.cms.service.CmsSiteService;
import cn.budingcc.framework.domain.cms.CmsSite;
import cn.budingcc.framework.domain.cms.request.ListCmsSiteParam;
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

import java.util.Optional;

/**
 * @author Ikaros
 * @date 2020/3/1 10:46
 */
@Service("CmsSiteService")
public class CmsSiteServiceImpl implements CmsSiteService {
    @Autowired
    CmsSiteRepository cmsSiteRepository;
    
    @Override
    public ResponseResult insertCmsSite(CmsSite cmsSite) {
        cmsSiteRepository.save(cmsSite);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult deleteCmsSite(String id) {
        cmsSiteRepository.deleteById(id);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult updateCmsSite(CmsSite cmsSite, String id) {
        cmsSiteRepository.save(cmsSite);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public QueryResponseResult listCmsSites(int page, int size, ListCmsSiteParam listCmsSiteParam) {
        CmsSite cmsSite = new CmsSite();
        String siteName = listCmsSiteParam.getSiteName();
        ExampleMatcher matcher = ExampleMatcher.matching();
        if (StringUtils.isNotEmpty(siteName)) {
            cmsSite.setSiteName(siteName);
            matcher.withMatcher("siteName", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        String serverDomain = listCmsSiteParam.getServerDomain();
        if (StringUtils.isNotEmpty(serverDomain)) {
            cmsSite.setServerPort(serverDomain);
            matcher.withMatcher("serverDomain", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        String serverPort = listCmsSiteParam.getServerPort();
        if (StringUtils.isNotEmpty(serverPort)) {
            cmsSite.setServerPort(serverPort);
            matcher.withMatcher("serverPort", ExampleMatcher.GenericPropertyMatchers.contains());
        }
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        Example<CmsSite> example = Example.of(cmsSite, matcher);
        Page<CmsSite> cmsSites = cmsSiteRepository.findAll(example, pageRequest);
        QueryResult<CmsSite> queryResult = new QueryResult<>();
        queryResult.setList(cmsSites.getContent());
        queryResult.setTotalPage(cmsSites.getTotalPages());
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
    
    @Override
    public CmsSite getCmsSiteById(String id) {
        Optional<CmsSite> optionalCmsSite = cmsSiteRepository.findById(id);
        return optionalCmsSite.orElseGet(null);
    }
}
