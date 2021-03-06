package cn.budingcc.es.service;

import cn.budingcc.framework.domain.shop.request.GoodSearchParam;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;

import java.io.IOException;

/**
 * @author Ikaros
 * @date 2020/2/23 21:10
 */
public interface EsShopService {
    QueryResponseResult listGoods(int page, int size, GoodSearchParam goodSearchParam);
    
    ResponseResult deleteGood(String id) throws IOException;
    
    ResponseResult deleteGoods(String ids) throws IOException;
}
