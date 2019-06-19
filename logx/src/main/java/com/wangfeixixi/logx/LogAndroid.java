package com.wangfeixixi.logx;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;

import static com.wangfeixixi.logx.Utils.checkNotNull;

public final class LogAndroid {

    //*************************常用方法***************************

    /**
     * 打印bean对象并格式化
     *
     * @param object bean对象
     */
    public static void bean(@Nullable Object object) {
        if (!LogXConfig.isApkInDebug()) return;
        LogPrinter.getInstance().json(JSON.toJSONString(object));
    }

    /**
     * 打印log
     *
     * @param object 任何类型
     */
    public static void d(@Nullable Object object) {
        if (!LogXConfig.isApkInDebug()) return;
        LogPrinter.getInstance().d(object);
    }

    /**
     * Formats the given json content and print it
     */
    public static void json(@Nullable String json) {
        if (!LogXConfig.isApkInDebug()) return;
        LogPrinter.getInstance().json(json);
    }

    /**
     * Formats the given xml content and print it
     */
    public static void xml(@Nullable String xml) {
        if (!LogXConfig.isApkInDebug()) return;
        LogPrinter.getInstance().xml(xml);
    }

    //******************************自定义方法**********************************

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    private LogAndroid() {
        //no instance
    }

    /**
     * 可增加自定义的logAdapter，implements IAdapter接口
     *
     * @param adapter IAdapter
     */
    public static void addLogAdapter(@NonNull IAdapter adapter) {
        if (!LogXConfig.isApkInDebug()) return;
        LogPrinter.getInstance().addAdapter(checkNotNull(adapter));
    }

    /**
     * 清除logAdapter
     */
    public static void clearLogAdapters() {
        if (!LogXConfig.isApkInDebug()) return;
        LogPrinter.getInstance().clearLogAdapters();
    }

    /**
     * 自定义log信息
     *
     * @param priority  log优先级
     * @param tag       log标签
     * @param message   log信息
     * @param throwable 异常
     */
    public static void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        if (!LogXConfig.isApkInDebug()) return;
        LogPrinter.getInstance().log(priority, tag, message, throwable);
    }

    /**
     * 打印debug log
     *
     * @param message log信息
     * @param args    格式
     */
    public static void d(@NonNull String message, @Nullable Object... args) {
        if (!LogXConfig.isApkInDebug()) return;
        LogPrinter.getInstance().d(message, args);
    }


    /**
     * 打印log
     *
     * @param message log信息
     * @param args    log对象
     */
    public static void e(@NonNull String message, @Nullable Object... args) {
        if (!LogXConfig.isApkInDebug()) return;
        LogPrinter.getInstance().e(null, message, args);
    }

    public static void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        if (!LogXConfig.isApkInDebug()) return;
        LogPrinter.getInstance().e(throwable, message, args);
    }

    public static void i(@NonNull String message, @Nullable Object... args) {
        if (!LogXConfig.isApkInDebug()) return;
        LogPrinter.getInstance().i(message, args);
    }

    public static void v(@NonNull String message, @Nullable Object... args) {
        if (!LogXConfig.isApkInDebug()) return;
        LogPrinter.getInstance().v(message, args);
    }

    public static void w(@NonNull String message, @Nullable Object... args) {
        if (!LogXConfig.isApkInDebug()) return;
        LogPrinter.getInstance().w(message, args);
    }

    /**
     * Tip: Use this for exceptional situations to d
     * ie: Unexpected errors etc
     */
    public static void wtf(@NonNull String message, @Nullable Object... args) {
        if (!LogXConfig.isApkInDebug()) return;
        LogPrinter.getInstance().wtf(message, args);
    }


}
