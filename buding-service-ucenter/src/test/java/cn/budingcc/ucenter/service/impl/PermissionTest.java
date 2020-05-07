package cn.budingcc.ucenter.service.impl;

import cn.budingcc.framework.domain.ucenter.BdMenu;
import cn.budingcc.ucenter.dao.BdMenuRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author Ikaros
 * @date 2020/3/26 16:32
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PermissionTest {
    @Autowired
    private BdMenuRepository bdMenuRepository;
    
    @Test
    public void selectRolesTest() {
        List<BdMenu> roles = bdMenuRepository.selectPermissionByUserId("123");
        System.out.println(roles);
    }
}
