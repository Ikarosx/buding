package cn.budingcc.shop.service.impl;

import cn.budingcc.framework.domain.shop.Good;
import cn.budingcc.framework.domain.shop.UserGoodFollow;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.QueryResult;
import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.shop.dao.FollowMapper;
import cn.budingcc.shop.dao.FollowRepository;
import cn.budingcc.shop.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Ikaros
 * @date 2020/1/29 14:12
 */
@Service("FollowService")
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowRepository followRepository;
    @Autowired
    FollowMapper followMapper;
    
    @Override
    public ResponseResult insertFollow(UserGoodFollow follow) {
        follow.setCreateTime(new Date());
        follow.setUpdateTime(new Date());
        followRepository.save(follow);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult deleteFollow(UserGoodFollow follow) {
        followRepository.deleteByUserIdAndGoodId(follow.getUserId(), follow.getGoodId());
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public QueryResponseResult listFollowsByUserId(String userId) {
        List<Good> goodList = followMapper.listFollowsByUserId(userId);
        QueryResult<Good> queryResult = new QueryResult<>();
        queryResult.setList(goodList);
        queryResult.setTotal(goodList.size());
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
}
