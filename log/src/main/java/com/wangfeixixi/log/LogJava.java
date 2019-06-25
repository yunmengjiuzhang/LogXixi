package com.wangfeixixi.log;

/**
 * 基于java环境的log打印
 */
public class LogJava {
    /**
     * 任何bean对象，或数据类型
     *
     * @param o debug
     */
    public static void d(Object o) {
        d(ToStringUtil.toString(o));
    }

    /**
     * 任何bean对象，或数据类型
     *
     * @param o debug
     */
    public static void e(Object o) {
        e(ToStringUtil.toString(o));
    }

    private static void d(String msg) {
        System.out.println(msg);
    }
    private static void e(String msg) {
        System.err.println(msg);
    }
}
