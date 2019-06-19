package com.wangfeixixi.logxixi;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.wangfeixixi.logxixi.Utils.checkNotNull;

/**
 * This is used to saves d messages to the disk.
 * By default it uses {@link DiskFormatStrategy} to translates text message into CSV format.
 */
public class DiskLogAdapter implements IAdapter {

  @NonNull private final IFormatStrategy formatStrategy;

  public DiskLogAdapter() {
    formatStrategy = DiskFormatStrategy.newBuilder().build();
  }

  public DiskLogAdapter(@NonNull IFormatStrategy formatStrategy) {
    this.formatStrategy = checkNotNull(formatStrategy);
  }

  @Override public boolean isLoggable(int priority, @Nullable String tag) {
    return true;
  }

  @Override public void log(int priority, @Nullable String tag, @NonNull String message) {
    formatStrategy.log(priority, tag, message);
  }
}
