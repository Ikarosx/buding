package cn.budingcc.api.shop;

import cn.budingcc.framework.domain.shop.Good;
import cn.budingcc.framework.domain.shop.request.GoodListRequest;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Ikaros
 * @date 2020/1/28 13:59
 */
@Api("商品接口")
public interface GoodControllerApi {
    @ApiOperation("添加商品")
    ResponseResult insertGood(Good good);
    
    @ApiOperation("修改商品")
    ResponseResult updateGood(Good good, String goodId);
    
    @ApiOperation("删除单个商品")
    ResponseResult deleteGood(String goodId);
    
    @ApiOperation("批量删除")
    ResponseResult deleteGoods(String ids);
    
    @ApiOperation("查找全部商品")
    QueryResponseResult listAllGoods(Integer status);
    
    @ApiOperation("根据商品ID查找单个商品")
    Good getGoodById(String goodId);
    
    @ApiOperation("根据条件查找多个商品")
    QueryResponseResult listGoodsByPage(int page, int size, GoodListRequest goodListRequest);
    
}
