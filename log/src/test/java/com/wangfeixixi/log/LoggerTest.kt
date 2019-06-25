package com.wangfeixixi.log

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

class LoggerTest {

    @Mock
    private lateinit var printer: IPrinter

    @Before
    fun setup() {
        initMocks(this)

//    LogAndroid.printer(printer)
    }

    @Test
    fun log() {
        val throwable = Throwable()
        LogAndroid.log(LogAndroid.VERBOSE, "tag", "message", throwable)

        verify(printer).log(LogAndroid.VERBOSE, "tag", "message", throwable)
    }

    @Test
    fun debugLog() {
        LogAndroid.d("message %s", "arg")

        verify(printer).d("message %s", "arg")
    }

    @Test
    fun verboseLog() {
        LogAndroid.v("message %s", "arg")

        verify(printer).v("message %s", "arg")
    }

    @Test
    fun warningLog() {
        LogAndroid.w("message %s", "arg")

        verify(printer).w("message %s", "arg")
    }

    @Test
    fun errorLog() {
        LogAndroid.e("message %s", "arg")

        verify(printer).e(null as Throwable?, "message %s", "arg")
    }

    @Test
    fun errorLogWithThrowable() {
        val throwable = Throwable("throwable")
        LogAndroid.e(throwable, "message %s", "arg")

        verify(printer).e(throwable, "message %s", "arg")
    }

    @Test
    fun infoLog() {
        LogAndroid.i("message %s", "arg")

        verify(printer).i("message %s", "arg")
    }

    @Test
    fun wtfLog() {
        LogAndroid.wtf("message %s", "arg")

        verify(printer).wtf("message %s", "arg")
    }

    @Test
    fun logObject() {
        val `object` = Any()
        LogAndroid.d(`object`)

        verify(printer).d(`object`)
    }

    @Test
    fun oneTimeTag() {
        `when`(printer.t("tag")).thenReturn(printer)

//    LogAndroid.t("tag").d("message")

        verify(printer).t("tag")
        verify(printer).d("message")
    }

    @Test
    fun addAdapter() {
        val adapter = mock(IAdapter::class.java)
        LogAndroid.addLogAdapter(adapter)

        verify(printer).addAdapter(adapter)
    }

    @Test
    fun clearLogAdapters() {
        LogAndroid.clearLogAdapters()

        verify(printer).clearLogAdapters()
    }
}
