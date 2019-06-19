package com.wangfeixixi.log

import android.util.Log
import com.google.common.truth.Truth.assertThat
import com.wangfeixixi.log.Utils.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.net.UnknownHostException

@RunWith(RobolectricTestRunner::class)
class UtilsTest {

  @Test fun isEmpty() {
    assertThat(isEmpty("")).isTrue()
    assertThat(isEmpty(null)).isTrue()
  }

  @Test fun equals() {
    assertThat(equals("a", "a")).isTrue()
    assertThat(equals("as", "b")).isFalse()
    assertThat(equals(null, "b")).isFalse()
    assertThat(equals("a", null)).isFalse()
  }

  @Test fun getStackTraceString() {
    val throwable = Throwable("test")
    val androidTraceString = Log.getStackTraceString(throwable)
    assertThat(getStackTraceString(throwable)).isEqualTo(androidTraceString)
  }

  @Test fun getStackTraceStringReturnsEmptyStringWithNull() {
    assertThat(getStackTraceString(null)).isEqualTo("")
  }

  @Test fun getStackTraceStringReturnEmptyStringWithUnknownHostException() {
    assertThat(getStackTraceString(UnknownHostException())).isEqualTo("")
  }

  @Test fun logLevels() {
    assertThat(logLevel(LogAndroid.DEBUG)).isEqualTo("DEBUG")
    assertThat(logLevel(LogAndroid.WARN)).isEqualTo("WARN")
    assertThat(logLevel(LogAndroid.INFO)).isEqualTo("INFO")
    assertThat(logLevel(LogAndroid.VERBOSE)).isEqualTo("VERBOSE")
    assertThat(logLevel(LogAndroid.ASSERT)).isEqualTo("ASSERT")
    assertThat(logLevel(LogAndroid.ERROR)).isEqualTo("ERROR")
    assertThat(logLevel(100)).isEqualTo("UNKNOWN")
  }

  @Test fun objectToString() {
    val `object` = "Test"

    assertThat(toString(`object`)).isEqualTo("Test")
  }

  @Test fun toStringWithNull() {
    assertThat(toString(null)).isEqualTo("null")
  }

  @Test fun primitiveArrayToString() {
    val booleanArray = booleanArrayOf(true, false, true)
    assertThat(toString(booleanArray)).isEqualTo("[true, false, true]")

    val byteArray = byteArrayOf(1, 0, 1)
    assertThat(toString(byteArray)).isEqualTo("[1, 0, 1]")

    val charArray = charArrayOf('a', 'b', 'c')
    assertThat(toString(charArray)).isEqualTo("[a, b, c]")

    val shortArray = shortArrayOf(1, 3, 5)
    assertThat(toString(shortArray)).isEqualTo("[1, 3, 5]")

    val intArray = intArrayOf(1, 3, 5)
    assertThat(toString(intArray)).isEqualTo("[1, 3, 5]")

    val longArray = longArrayOf(1, 3, 5)
    assertThat(toString(longArray)).isEqualTo("[1, 3, 5]")

    val floatArray = floatArrayOf(1f, 3f, 5f)
    assertThat(toString(floatArray)).isEqualTo("[1.0, 3.0, 5.0]")

    val doubleArray = doubleArrayOf(1.0, 3.0, 5.0)
    assertThat(toString(doubleArray)).isEqualTo("[1.0, 3.0, 5.0]")
  }

  @Test fun multiDimensionArrayToString() {
    val `object` = arrayOf(intArrayOf(1, 2, 3), intArrayOf(4, 5, 6))

    assertThat(toString(`object`)).isEqualTo("[[1, 2, 3], [4, 5, 6]]")
  }
}