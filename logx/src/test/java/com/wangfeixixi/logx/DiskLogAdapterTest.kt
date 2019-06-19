package com.wangfeixixi.logx

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks

class DiskLogAdapterTest {

  @Mock private lateinit var formatStrategy: IFormatStrategy

  @Before fun setup() {
    initMocks(this)
  }

  @Test fun isLoggableTrue() {
    val logAdapter = DiskLogAdapter(formatStrategy)

    assertThat(logAdapter.isLoggable(LogAndroid.VERBOSE, "tag")).isTrue()
  }

  @Test fun isLoggableFalse() {
    val logAdapter = object : DiskLogAdapter(formatStrategy) {
      override fun isLoggable(priority: Int, tag: String?): Boolean {
        return false
      }
    }

    assertThat(logAdapter.isLoggable(LogAndroid.VERBOSE, "tag")).isFalse()
  }

  @Test fun log() {
    val logAdapter = DiskLogAdapter(formatStrategy)

    logAdapter.log(LogAndroid.VERBOSE, "tag", "message")

    verify(formatStrategy).log(LogAndroid.VERBOSE, "tag", "message")
  }

}
