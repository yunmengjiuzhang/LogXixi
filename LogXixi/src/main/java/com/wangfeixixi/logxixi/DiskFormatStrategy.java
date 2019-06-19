package com.wangfeixixi.logxixi;

import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.wangfeixixi.logxixi.Utils.checkNotNull;

/**
 * CSV formatted file logging for Android.
 * Writes to CSV the following data:
 * epoch timestamp, ISO8601 timestamp (human-readable), d level, tag, d message.
 */
public class DiskFormatStrategy implements IFormatStrategy {

    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String NEW_LINE_REPLACEMENT = " <br> ";
    private static final String SEPARATOR = "|";

    //    @NonNull
//    private final Date date;
//    @NonNull
//    private final SimpleDateFormat dateFormat;
    @NonNull
    private final ILogStrategy logStrategy;
    @Nullable
    private final String tag;

    private DiskFormatStrategy(@NonNull Builder builder) {
        checkNotNull(builder);

        logStrategy = builder.logStrategy;
        tag = builder.tag;
    }

    @NonNull
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override
    public void log(int priority, @Nullable String onceOnlyTag, @NonNull String message) {
        checkNotNull(message);

        //log 内容
        String tag = formatTag(onceOnlyTag);

        StringBuilder builder = new StringBuilder();

        // human-readable date/time
        builder.append(LogXixi.getTime());

        builder.append(SEPARATOR);
        builder.append(PrintUtils.getStackTraceInfo());

        // level
        builder.append(SEPARATOR);
        builder.append(Utils.logLevel(priority));

        // tag
        builder.append(SEPARATOR);
        builder.append(tag);

        // message
        if (message.contains(NEW_LINE)) {
            // a new line would break the CSV format, so we replace it here
            message = message.replaceAll(NEW_LINE, NEW_LINE_REPLACEMENT);
        }
        builder.append(SEPARATOR);

        StringBuffer sb = new StringBuffer();
        String[] split = message.split("<br>");
        for (String str : split)
            sb.append(str);

        builder.append(sb.toString().trim());

        // new line
        builder.append(NEW_LINE);

        logStrategy.log(priority, tag, builder.toString());
    }


    @Nullable
    private String formatTag(@Nullable String tag) {
        if (!Utils.isEmpty(tag) && !Utils.equals(this.tag, tag)) {
            return this.tag + "-" + tag;
        }
        return this.tag;
    }

    public static final class Builder {


        //        Date date;
//        SimpleDateFormat dateFormat;
        ILogStrategy logStrategy;
        String tag = "PRETTY_LOGGER";

        private Builder() {
        }

        @NonNull
        public Builder logStrategy(@Nullable ILogStrategy val) {
            logStrategy = val;
            return this;
        }

        @NonNull
        public Builder tag(@Nullable String tag) {
            this.tag = tag;
            return this;
        }

        @NonNull
        public DiskFormatStrategy build() {
            if (logStrategy == null) {
                HandlerThread ht = new HandlerThread("AndroidFileLogger." + LogXixi.getDirPath());
                ht.start();
                Handler handler = new DiskLogStrategy.WriteHandler(ht.getLooper());
                logStrategy = new DiskLogStrategy(handler);
            }
            return new DiskFormatStrategy(this);
        }
    }
}
