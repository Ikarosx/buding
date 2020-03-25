package cn.budingcc.framework.util;

import java.util.regex.Pattern;

/**
 * @author Ikaros
 * @date 2020/2/11 22:07
 */
public class ValidateUtil {
    /**
     * 用户名: 数字字母
     */
    public static boolean validateUser(String string) {
        return Pattern.matches("[^a-zA-Z0-9]+", string);
    }
    
}
