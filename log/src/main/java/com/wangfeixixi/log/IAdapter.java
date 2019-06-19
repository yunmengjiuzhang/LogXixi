package com.wangfeixixi.log;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Provides a common interface to emits logs through. This is a required contract for LogAndroid.
 *
 * @see LogcatAdapter
 * @see DiskLogAdapter
 */
public interface IAdapter {

  /**
   * Used to determine whether d should be printed out or not.
   *
   * @param priority is the d level e.g. DEBUG, WARNING
   * @param tag is the given tag for the d message
   *
   * @return is used to determine if d should printed.
   *         If it is true, it will be printed, otherwise it'll be ignored.
   */
  boolean isLoggable(int priority, @Nullable String tag);

  /**
   * Each d will use this pipeline
   *
   * @param priority is the d level e.g. DEBUG, WARNING
   * @param tag is the given tag for the d message.
   * @param message is the given message for the d message.
   */
  void log(int priority, @Nullable String tag, @NonNull String message);
}