package cn.budingcc.shop.controller;

import cn.budingcc.api.shop.GoodControllerApi;
import cn.budingcc.framework.domain.shop.Good;
import cn.budingcc.framework.domain.shop.request.GoodListRequest;
import cn.budingcc.framework.exception.ExceptionCast;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.shop.service.GoodService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author x5322
 */
@RestController
@RequestMapping("good")
public class GoodController implements GoodControllerApi {
    @Autowired
    private GoodService goodService;
    
    
    @Override
    @PostMapping
    public ResponseResult insertGood(@RequestBody Good good) {
        goodParamValidate(good);
        return goodService.insertGood(good);
    }
    
    
    @Override
    @PutMapping("/{goodId}")
    public ResponseResult updateGood(@RequestBody Good good, @PathVariable String goodId) {
        goodParamValidate(good);
        return goodService.updateGood(good, goodId);
    }
    
    @Override
    @DeleteMapping("/{goodId}")
    public ResponseResult deleteGood(@PathVariable String goodId) {
        return goodService.deleteGood(goodId);
    }
    
    @Override
    @DeleteMapping("/list/{ids}")
    public ResponseResult deleteGoods(@PathVariable String ids) {
        return goodService.deleteGoods(ids);
    }
    
    @Override
    @GetMapping
    public QueryResponseResult listAllGoods(Integer status) {
        return goodService.listAllGoods(status);
    }
    
    @Override
    @GetMapping("/{goodId}")
    public Good getGoodById(String goodId) {
        return goodService.getGoodById(goodId);
    }
    
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult listGoodsByPage(@PathVariable int page, @PathVariable int size, GoodListRequest goodListRequest) {
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 6;
        }
        return goodService.listGoodsByPage(page, size, goodListRequest);
    }
    
    private void goodParamValidate(@RequestBody Good good) {
        Double price = good.getPrice();
        String userId = good.getUserId();
        if (StringUtils.isEmpty(userId)) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
        if (price == null || price < 0) {
            ExceptionCast.cast(CommonCodeEnum.INVALID_PARAM);
        }
    }
}
