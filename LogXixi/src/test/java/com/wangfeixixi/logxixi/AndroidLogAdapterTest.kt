package com.wangfeixixi.logxixi

import org.junit.Test

import com.google.common.truth.Truth.assertThat
import com.wangfeixixi.logxixi.LogAndroid.DEBUG
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class AndroidLogAdapterTest {

  @Test fun isLoggable() {
    val logAdapter = LogcatAdapter()

    assertThat(logAdapter.isLoggable(DEBUG, "tag")).isTrue()
  }

  @Test fun log() {
    val formatStrategy = mock(IFormatStrategy::class.java)
    val logAdapter = LogcatAdapter(formatStrategy)

    logAdapter.log(DEBUG, null, "message")

    verify(formatStrategy).log(DEBUG, null, "message")
  }

}