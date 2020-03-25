package cn.budingcc.shop.client;

import cn.budingcc.framework.model.response.ResponseResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Ikaros
 * @date 2020/2/28 13:02
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EsShopClientTest {
    @Autowired EsShopClient esShopClient;
    
    @Test
    public void esShopClientTest(){
        ResponseResult responseResult = esShopClient.deleteGood("213");
        System.out.println(responseResult);
    }
}
