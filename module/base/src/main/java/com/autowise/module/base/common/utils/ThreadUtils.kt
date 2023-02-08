package com.autowise.module.base.common.utils

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class ThreadUtils {
    private val handlerThreadMap: ConcurrentHashMap<String?, HandlerThread> = ConcurrentHashMap()
    private var asyncHandler: Handler? = null
    private var mainHandler: Handler? = null
    private val handlerThreadLooper: Looper
        get() {
            return getHandlerThreadLooper(null)
        }

    @Synchronized
    fun getHandlerThreadLooper(name: String? = null): Looper {
        var finalName: String? = name
        if (finalName.isNullOrEmpty()) {
            finalName = HANDLER_THREAD_NAME_DEFAULT
        }
        var handlerThread: HandlerThread? = handlerThreadMap[finalName]
        if (handlerThread == null) {
            handlerThread = HandlerThread(finalName)
            handlerThread.start()
            handlerThreadMap.putIfAbsent(finalName, handlerThread)
        }
        return handlerThread.looper
    }

    @Synchronized
    fun quitHandThreadLooper(name: String?) {
        if (!name.isNullOrEmpty()) {
            handlerThreadMap.remove(name)?.apply {
                quitSafely()
            }
        }
    }

    @Synchronized
    private fun initAsyncHandler() {
        if (asyncHandler == null) {
            asyncHandler = Handler(handlerThreadLooper)
        }
    }

    fun executeSync(runnable: Runnable) {
        if (asyncHandler == null) {
            initAsyncHandler()
        }
        asyncHandler!!.post(runnable)
    }

    fun executeSync(runnable: Runnable, delay: Long) {
        if (asyncHandler == null) {
            initAsyncHandler()
        }
        asyncHandler!!.postDelayed(runnable, delay)
    }

    @Synchronized
    private fun initMainHandler() {
        if (mainHandler == null) {
            mainHandler = Handler(Looper.getMainLooper())
        }
    }

    fun executeMain(runnable: Runnable) {
        if (mainHandler == null) {
            initMainHandler()
        }
        mainHandler!!.post(runnable)
    }

    fun executeMain(runnable: Runnable, delay: Long) {
        if (mainHandler == null) {
            initMainHandler()
        }
        mainHandler!!.postDelayed(runnable, delay)
    }

    fun cancelMain(runnable: Runnable) {
        if (mainHandler != null) {
            mainHandler!!.removeCallbacks(runnable)
        }
    }

    fun execute(runnable: Runnable?): ScheduledFuture<*> {
        return ThreadPoolUtils.sThreadPool.schedule(runnable, 0, TimeUnit.MILLISECONDS)
    }

    fun execute(runnable: Runnable?, delay: Long): ScheduledFuture<*> {
        return ThreadPoolUtils.sThreadPool.schedule(
            runnable,
            delay,
            TimeUnit.MILLISECONDS
        )
    }

    fun executeFixedRate(runnable: Runnable?, initDelay: Long, period: Long): ScheduledFuture<*> {
        return ThreadPoolUtils.sThreadPool.scheduleAtFixedRate(
            runnable,
            initDelay,
            period,
            TimeUnit.MILLISECONDS
        )
    }

    fun executeFixedDelay(runnable: Runnable?, initDelay: Long, delay: Long): ScheduledFuture<*> {
        return ThreadPoolUtils.sThreadPool.scheduleWithFixedDelay(
            runnable,
            initDelay,
            delay,
            TimeUnit.MILLISECONDS
        )
    }

    val executor: ThreadPoolUtils
        get() {
            return ThreadPoolUtils.sThreadPool
        }

    fun cancelRunnable(runnable: Runnable?): Boolean {
        return ThreadPoolUtils.sThreadPool.remove(runnable)
    }

    companion object {
        private val sInstance: ThreadUtils = ThreadUtils()
        private const val HANDLER_THREAD_NAME_DEFAULT: String = "custom-handler-thread"

        fun get(): ThreadUtils {
            return sInstance
        }

        fun sleep(millis: Long) {
            try {
                Thread.sleep(millis)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}