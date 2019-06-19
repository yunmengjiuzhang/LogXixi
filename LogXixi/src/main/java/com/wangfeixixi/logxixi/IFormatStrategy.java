package com.wangfeixixi.logxixi;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Used to determine how messages should be printed or saved.
 *
 * @see LogcatFormatStrategy
 * @see DiskFormatStrategy
 */
public interface IFormatStrategy {

  void log(int priority, @Nullable String tag, @NonNull String message);
}
