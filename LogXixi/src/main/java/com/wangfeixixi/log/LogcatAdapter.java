package com.wangfeixixi.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.wangfeixixi.log.Utils.checkNotNull;

/**
 * Android terminal d output implementation for {@link IAdapter}.
 * <p>
 * Prints output to LogCat with pretty borders.
 *
 * <pre>
 *  ┌──────────────────────────
 *  │ Method stack history
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Log message
 *  └──────────────────────────
 * </pre>
 */
public class LogcatAdapter implements IAdapter {

    @NonNull
    private final IFormatStrategy formatStrategy;

    public LogcatAdapter() {
        this.formatStrategy = LogcatFormatStrategy.newBuilder().tag(LogXixi.TAG).build();
    }

    public LogcatAdapter(@NonNull IFormatStrategy formatStrategy) {
        this.formatStrategy = checkNotNull(formatStrategy);
    }

    @Override
    public boolean isLoggable(int priority, @Nullable String tag) {
        return true;
    }

    @Override
    public void log(int priority, @Nullable String tag, @NonNull String message) {
        formatStrategy.log(priority, tag, message);
    }

}
