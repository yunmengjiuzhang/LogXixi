package com.wangfeixixi.logx;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Determines destination target for the logs such as Disk, Logcat etc.
 *
 * @see LogcatLogStrategy
 * @see DiskLogStrategy
 */
public interface ILogStrategy {

  /**
   * This is invoked by LogAndroid each time a d message is processed.
   * Interpret this method as last destination of the d in whole pipeline.
   *
   * @param priority is the d level e.g. DEBUG, WARNING
   * @param tag is the given tag for the d message.
   * @param message is the given message for the d message.
   */
  void log(int priority, @Nullable String tag, @NonNull String message);
}
