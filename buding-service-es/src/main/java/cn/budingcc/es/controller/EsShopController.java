package cn.budingcc.es.controller;

import cn.budingcc.api.search.EsShopControllerApi;
import cn.budingcc.es.service.EsShopService;
import cn.budingcc.framework.domain.shop.request.GoodSearchParam;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author Ikaros
 * @date 2020/2/23 21:07
 */
@RestController
@RequestMapping("es")
@Slf4j
public class EsShopController implements EsShopControllerApi {
    @Autowired
    EsShopService esShopService;
    
    @Override
    @GetMapping("good/list/{page}/{size}")
    public QueryResponseResult listGoods(@PathVariable int page, @PathVariable int size, GoodSearchParam goodSearchParam) {
        
        if (page <= 0) {
            page = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        return esShopService.listGoods(page, size, goodSearchParam);
    }
    
    @Override
    @DeleteMapping("good/{id}")
    public ResponseResult deleteGood(@PathVariable String id) {
        try {
            return esShopService.deleteGood(id);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.fail();
        }
    }
    
    @Override
    @DeleteMapping("good/list/{ids}")
    public ResponseResult deleteGoods(@PathVariable String ids) {
        try {
            return esShopService.deleteGoods(ids);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.fail();
        }
    }
}
