package cn.budingcc.framework.model.response;

/**
 * @author Ikaros
 * @date 2020/1/26 16:59
 */
public interface ResultCode {
    /**
     * @return 操作是否成功, true为成功，false操作失败
     */
    boolean success();

    /**
     * 10000-- 通用错误代码
     * 22000-- 二手系统错误代码
     * 23000-- 单词系统错误代码
     * 24000-- 表白系统错误代码
     * 25000-- 班级系统错误代码
     * 26000-- 文件系统错误代码
     * 27000-- 搜索系统错误代码
     * 30000-- 认证系统
     *
     * @return 操作代码
     */
    int code();

    /**
     * @return 提示信息
     */
    String message();
}
