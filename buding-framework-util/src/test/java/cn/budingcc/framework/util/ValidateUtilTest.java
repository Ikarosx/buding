package cn.budingcc.framework.util;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * @author Ikaros
 * @date 2020/2/11 22:19
 */
public class ValidateUtilTest {
    @Test
    public void userTest() {
        String regex = "[0-9a-zA-Z\u4e00-\u9fa5]{1,10}";
        boolean flag = Pattern.matches(regex, "广东第二师范学院");
        System.out.println("Byte\t\t我想在127岁之前和你");
        System.out.println("Short\t\t牵32767次手");
        System.out.println("Character\t逛65535个景点") ;
        System.out.println("Integer\t\t写2147483647行代码");
        System.out.println("Long\t\t赚9223372036854775807块钱");
        System.out.println("Float\t\t说3.4028235E38句话");
        System.out.println("Double\t\t看1.7976931348623157E308眼");
        System.out.println();
        System.out.println("BigDecimal\t未来还想走BigDecimal.MAX_VALUE步路");
        
        
    }
}
