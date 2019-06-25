package com.wangfeixixi.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.wangfeixixi.log.LogAndroid.ASSERT;
import static com.wangfeixixi.log.LogAndroid.DEBUG;
import static com.wangfeixixi.log.LogAndroid.ERROR;
import static com.wangfeixixi.log.LogAndroid.INFO;
import static com.wangfeixixi.log.LogAndroid.VERBOSE;
import static com.wangfeixixi.log.LogAndroid.WARN;
import static com.wangfeixixi.log.Utils.checkNotNull;

class LogPrinter implements IPrinter {

    private LogPrinter() {
        //log窗口
        addAdapter(new LogcatAdapter(LogcatFormatStrategy.newBuilder().tag(LogXixi.TAG).build()));
        //disk 缓存
        addAdapter(new LogcatAdapter(DiskFormatStrategy.newBuilder().tag(LogXixi.TAG).build()));
    }

    private static class Inner {
        private static LogPrinter instance = new LogPrinter();
    }

    public static LogPrinter getInstance() {
        return Inner.instance;
    }

    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 2;

    /**
     * Provides one-time used tag for the d message
     */
    private final ThreadLocal<String> localTag = new ThreadLocal<>();

    private final List<IAdapter> logAdapters = new ArrayList<>();

    @Override
    public IPrinter t(String tag) {
        if (tag != null) {
            localTag.set(tag);
        }
        return this;
    }

    @Override
    public void d(@NonNull String message, @Nullable Object... args) {
        log(DEBUG, null, message, args);
    }

    @Override
    public void d(@Nullable Object msg) {
        log(DEBUG, null, msg.toString());
    }

    @Override
    public void e(@NonNull String message, @Nullable Object... args) {
        e(null, message, args);
    }

    @Override
    public void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        log(ERROR, throwable, message, args);
    }

    @Override
    public void w(@NonNull String message, @Nullable Object... args) {
        log(WARN, null, message, args);
    }

    @Override
    public void i(@NonNull String message, @Nullable Object... args) {
        log(INFO, null, message, args);
    }

    @Override
    public void v(@NonNull String message, @Nullable Object... args) {
        log(VERBOSE, null, message, args);
    }

    @Override
    public void wtf(@NonNull String message, @Nullable Object... args) {
        log(ASSERT, null, message, args);
    }

    @Override
    public synchronized void log(int priority,
                                 @Nullable String tag,
                                 @Nullable String message,
                                 @Nullable Throwable throwable) {
        if (throwable != null && message != null) {
            message += " : " + Utils.getStackTraceString(throwable);
        }
        if (throwable != null && message == null) {
            message = Utils.getStackTraceString(throwable);
        }
        if (Utils.isEmpty(message)) {
            message = "Empty/NULL d message";
        }

        for (IAdapter adapter : logAdapters) {
            if (adapter.isLoggable(priority, tag)) {
                adapter.log(priority, tag, message);
            }
        }
    }

    @Override
    public void clearLogAdapters() {
        logAdapters.clear();
    }

    @Override
    public void addAdapter(@NonNull IAdapter adapter) {
        logAdapters.add(checkNotNull(adapter));
    }

    /**
     * This method is synchronized in order to avoid messy of logs' order.
     */
    private synchronized void log(int priority,
                                  @Nullable Throwable throwable,
                                  @NonNull String msg,
                                  @Nullable Object... args) {
        checkNotNull(msg);

        String tag = getTag();
        String message = createMessage(msg, args);
        log(priority, tag, message, throwable);
    }

    /**
     * @return the appropriate tag based on local or global
     */
    @Nullable
    private String getTag() {
        String tag = localTag.get();
        if (tag != null) {
            localTag.remove();
            return tag;
        }
        return null;
    }

    @NonNull
    private String createMessage(@NonNull String message, @Nullable Object... args) {
        return args == null || args.length == 0 ? message : String.format(message, args);
    }
}
