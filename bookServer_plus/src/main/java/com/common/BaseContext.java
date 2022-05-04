package com.common;

/**
 * 基于ThreadLocal封装的工具类，用于保存和获取当前线程的数据
 */
public class BaseContext {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    public static void setCurrentId(String id) {
        threadLocal.set(id);
    }

    public static String getCurrentId() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
