package cn.budingcc.shop.client;

import cn.budingcc.framework.client.BuDingServiceEnum;
import cn.budingcc.framework.model.response.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Ikaros
 * @date 2020/2/28 12:47
 */
@FeignClient(value = BuDingServiceEnum.BD_SERVICE_ES, fallback = EsShopServiceFallback.class)
public interface EsShopClient {
    @DeleteMapping("es/good/{id}")
    ResponseResult deleteGood(@PathVariable String id);
    
    @DeleteMapping("es/good/list/{ids}")
    ResponseResult deleteGoods(@PathVariable String ids);
}
