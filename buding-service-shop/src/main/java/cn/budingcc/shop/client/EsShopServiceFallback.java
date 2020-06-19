package cn.budingcc.shop.client;

import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.ResponseResult;
import org.springframework.stereotype.Component;

/**
 * @author Ikaros
 * @date 2020/6/10 20:53
 */
@Component
public class EsShopServiceFallback implements EsShopClient {
    @Override
    public ResponseResult deleteGood(String id) {
        return new ResponseResult(CommonCodeEnum.FAIL);
    }
    
    @Override
    public ResponseResult deleteGoods(String ids) {
        return new ResponseResult(CommonCodeEnum.FAIL);
    }
}
