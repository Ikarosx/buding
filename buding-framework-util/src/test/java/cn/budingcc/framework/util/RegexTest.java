package cn.budingcc.framework.util;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * @author Ikaros
 * @date 2020/2/15 17:09
 */
public class RegexTest {
    @Test
    public void regexTest() {
        // 1-1-20
        String regex = "[0-9-]+";
        System.out.println(Pattern.matches(regex,"0-02+"));
    }
}
