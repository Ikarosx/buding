package cn.ikarosx.spring;

import cn.budingcc.framework.domain.shop.Good;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Ikaros
 * @date 2020/4/30 9:39
 */
public class SpringTest {
    @Test
    public void test() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
        Good good = (Good) applicationContext.getBean("good");
        System.out.println(good);
    }
}
