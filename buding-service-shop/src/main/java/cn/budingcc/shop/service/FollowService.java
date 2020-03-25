package cn.budingcc.shop.service;

import cn.budingcc.framework.domain.shop.UserGoodFollow;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;

/**
 * @author Ikaros
 * @date 2020/1/29 14:12
 */
public interface FollowService {
    ResponseResult insertFollow(UserGoodFollow follow);
    
    ResponseResult deleteFollow(UserGoodFollow follow);
    
    QueryResponseResult listFollowsByUserId(String userId);
}
