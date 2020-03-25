package cn.budingcc.api.shop;

import cn.budingcc.framework.domain.shop.UserGoodFollow;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Ikaros
 * @date 2020/1/29 14:06
 */
@Api(value = "关注")
public interface FollowControllerApi {
    @ApiOperation(value = "添加关注")
    ResponseResult insertFollow(UserGoodFollow follow);
    
    @ApiOperation(value = "取消关注")
    ResponseResult deleteFollow(UserGoodFollow follow);
    
    @ApiOperation(value = "查找用户所关注列表")
    QueryResponseResult listFollowsByUserId(String userId);
}
