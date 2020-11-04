package cn.budingcc.shop.service.impl;

import cn.budingcc.framework.domain.shop.Good;
import cn.budingcc.framework.domain.shop.request.GoodListRequest;
import cn.budingcc.framework.model.response.CommonCodeEnum;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.framework.model.response.QueryResult;
import cn.budingcc.framework.model.response.ResponseResult;
import cn.budingcc.framework.util.SecurityUtils;
import cn.budingcc.shop.client.EsShopClient;
import cn.budingcc.shop.config.RabbitConfig;
import cn.budingcc.shop.dao.GoodRepository;
import cn.budingcc.shop.service.GoodService;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author Ikaros
 * @date 2020/1/28 13:24
 */
@Service("GoodService")
public class GoodServiceImpl implements GoodService {
    @Autowired
    GoodRepository goodRepository;
    @Autowired
    EsShopClient esShopClient;
    @Autowired
    RabbitTemplate rabbitTemplate;
    
    @Override
    public ResponseResult insertGood(Good good) {
        good.setReportCount(0L);
        good.setBrowseCount(0L);
        good.setCreateTime(new Date());
        good.setUpdateTime(good.getCreateTime());
        good.setUsername(SecurityUtils.getUsernameFromAuth());
        good.setUserId(SecurityUtils.getIdFromAuth());
        good.setState(1);
        Good save = goodRepository.save(good);
        rabbitTemplate.convertAndSend(RabbitConfig.BD_THYMELEAF_EXCHANGE, RabbitConfig.BD_THYMELEAF_GOOD_INSERT_ROUTING_KEY, SerializationUtils.serialize(save));
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    public ResponseResult updateGood(Good good, String goodId) {
        good.setUpdateTime(new Date());
        goodRepository.save(good);
        return new ResponseResult(CommonCodeEnum.SUCCESS);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteGood(String goodId) {
        goodRepository.deleteById(goodId);
        ResponseResult responseResult = esShopClient.deleteGood(goodId);
        return responseResult;
    }
    
    @Override
    public QueryResponseResult listAllGoods(Integer state) {
        Good good = new Good();
        if (state != null) {
            if (state == 0 || state == 1) {
                good.setState(state);
            }
        }
        Example<Good> example = Example.of(good);
        List<Good> goodList = goodRepository.findAll(example);
        QueryResult<Good> queryResult = new QueryResult<>();
        queryResult.setList(goodList);
        queryResult.setTotal(goodList.size());
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
    
    @Override
    public Good getGoodById(String goodId) {
        Optional<Good> optional = goodRepository.findById(goodId);
        return optional.orElse(null);
    }
    
    @Override
    public QueryResponseResult listGoodsByPage(int page, int size, GoodListRequest goodListRequest) {
        Good good = new Good();
        Integer status = goodListRequest.getState();
        // 数据校验
        if (status != null) {
            good.setState(status);
        }
        String userId = goodListRequest.getUserId();
        if (!StringUtils.isEmpty(userId)) {
            good.setUserId(userId);
        }
        Example<Good> example = Example.of(good);
        // 排序
        String sortStr = goodListRequest.getSort();
        List<Sort.Order> orders = new LinkedList<>();
        if (!StringUtils.isEmpty(sortStr)) {
            for (String str : sortStr.split("-")) {
                String[] split = str.split(":");
                if (split.length != 2) {
                    continue;
                }
                String asc = split[1];
                Sort.Order order = "1".equals(asc) ? Sort.Order.asc(split[0]) : Sort.Order.desc(split[0]);
                orders.add(order);
            }
        }
        Sort sort = Sort.by(orders);
        // 分页
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        
        Page<Good> goodPage = goodRepository.findAll(example, pageable);
        QueryResult<Good> queryResult = new QueryResult<>();
        queryResult.setTotalPage(goodPage.getTotalPages());
        queryResult.setTotal(goodPage.getTotalElements());
        queryResult.setList(goodPage.getContent());
        return new QueryResponseResult(CommonCodeEnum.SUCCESS, queryResult);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteGoods(String ids) {
        List<Good> goodList = new LinkedList<>();
        for (String id : ids.split("-")) {
            Good good = new Good();
            good.setId(id);
            goodList.add(good);
        }
        goodRepository.deleteInBatch(goodList);
        ResponseResult responseResult = esShopClient.deleteGoods(ids);
        return responseResult;
    }
}
