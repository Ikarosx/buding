package cn.budingcc.shop.service;

import cn.budingcc.framework.domain.shop.Good;
import cn.budingcc.framework.domain.shop.request.GoodListRequest;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;

/**
 * @author Ikaros
 * @date 2020/1/28 13:24
 */
public interface GoodService {
    ResponseResult insertGood(Good good);
    
    ResponseResult updateGood(Good good, String goodId);
    
    ResponseResult deleteGood(String goodId);
    
    QueryResponseResult listAllGoods(Integer status);
    
    Good getGoodById(String goodId);
    
    QueryResponseResult listGoodsByPage(int page, int size, GoodListRequest goodListRequest);
    
    ResponseResult deleteGoods(String ids);
}
