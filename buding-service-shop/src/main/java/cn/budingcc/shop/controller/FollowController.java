package cn.budingcc.shop.controller;

import cn.budingcc.api.shop.FollowControllerApi;
import cn.budingcc.framework.domain.shop.Good;
import cn.budingcc.framework.domain.shop.UserGoodFollow;
import cn.budingcc.framework.domain.shop.response.ShopEnum;
import cn.budingcc.framework.exception.ExceptionCast;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.shop.service.FollowService;
import cn.budingcc.shop.service.GoodService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ikaros
 * @date 2020/1/29 14:11
 */
@RestController
@RequestMapping("follow")
public class FollowController implements FollowControllerApi {
    @Autowired
    FollowService followService;
    @Autowired
    GoodService goodService;
    
    @Override
    @PostMapping
    public ResponseResult insertFollow(@RequestBody UserGoodFollow follow) {
        // 验证
        String goodId = follow.getGoodId();
        String userId = follow.getUserId();
        if (StringUtils.isEmpty(goodId) || StringUtils.isEmpty(userId)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        // 不能关注自己
        Good good = goodService.getGoodById(goodId);
        if (good == null) {
            ExceptionCast.cast(ShopEnum.GOOD_NOT_FOUND);
        }
        if (good.getUserId().equals(userId)) {
            ExceptionCast.cast(ShopEnum.FOLLOW_SELF_ERROR);
        }
        return followService.insertFollow(follow);
    }
    
    @Override
    @DeleteMapping
    public ResponseResult deleteFollow(@RequestBody UserGoodFollow userGoodFollow) {
        return followService.deleteFollow(userGoodFollow);
    }
    
    @Override
    @GetMapping("/list/{userId}")
    public QueryResponseResult listFollowsByUserId(@PathVariable String userId) {
        return followService.listFollowsByUserId(userId);
    }
}
