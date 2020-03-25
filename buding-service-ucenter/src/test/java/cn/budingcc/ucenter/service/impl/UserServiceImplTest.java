package cn.budingcc.ucenter.service.impl;

import cn.budingcc.framework.domain.ucenter.extension.BdUserRoleExtension;
import cn.budingcc.framework.domain.ucenter.request.UserListRequest;
import cn.budingcc.framework.model.response.QueryResponseResult;
import cn.budingcc.ucenter.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/2/11 18:12
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceImplTest {
    @Autowired
    UserService userService;
    
    @Test
    public void listUsersByPageTest() {
        UserListRequest userListRequest = new UserListRequest();
        //        userListRequest.setSex(1);
        QueryResponseResult queryResponseResult = userService.listUsersByPage(1, 10, userListRequest);
        List<BdUserRoleExtension> list = queryResponseResult.getQueryResult().getList();
        for (BdUserRoleExtension bdUserRoleExtension : list) {
            System.out.println(bdUserRoleExtension);
        }
    }
}
