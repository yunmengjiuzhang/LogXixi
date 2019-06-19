package com.wangfeixixi.log

import org.junit.Test

import com.google.common.truth.Truth.assertThat

class CsvFormatStrategyTest {

  @Test fun log() {
    val formatStrategy = DiskFormatStrategy.newBuilder()
        .logStrategy { priority, tag, message ->
          assertThat(tag).isEqualTo("PRETTY_LOGGER-tag")
          assertThat(priority).isEqualTo(LogAndroid.VERBOSE)
          assertThat(message).contains("VERBOSE,PRETTY_LOGGER-tag,message")
        }
        .build()

    formatStrategy.log(LogAndroid.VERBOSE, "tag", "message")
  }

  @Test fun defaultTag() {
    val formatStrategy = DiskFormatStrategy.newBuilder()
        .logStrategy { priority, tag, message -> assertThat(tag).isEqualTo("PRETTY_LOGGER") }
        .build()

    formatStrategy.log(LogAndroid.VERBOSE, null, "message")
  }

  @Test fun customTag() {
    val formatStrategy = DiskFormatStrategy.newBuilder()
        .tag("custom")
        .logStrategy { priority, tag, message -> assertThat(tag).isEqualTo("custom") }
        .build()

    formatStrategy.log(LogAndroid.VERBOSE, null, "message")
  }
}
