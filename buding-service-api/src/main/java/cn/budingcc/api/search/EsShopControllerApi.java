package cn.budingcc.api.search;

import cn.budingcc.framework.domain.shop.request.GoodSearchParam;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Ikaros
 * @date 2020/2/23 20:54
 */
@Api("Es商品接口")
public interface EsShopControllerApi {
    
    @ApiOperation("商品搜索")
    QueryResponseResult listGoods(int page, int size, GoodSearchParam goodSearchParam);
    
    @ApiOperation("删除商品")
    ResponseResult deleteGood(String id);
    
    @ApiOperation("批量删除商品")
    ResponseResult deleteGoods(String ids);
}
