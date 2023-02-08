package com.autowise.module.base.common.utils
import android.os.Process
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

class ThreadPoolUtils private constructor(
    corePoolSize: Int,
    threadFactory: ThreadFactory
) : ScheduledThreadPoolExecutor(corePoolSize, threadFactory) {
    companion object {
        private val CPU_COUNT: Int = Runtime.getRuntime().availableProcessors()
        private val MINIMUM_CPU_COUNT: Int = if (CPU_COUNT < 4) 4 else CPU_COUNT
        private val CORE_POOL_SIZE: Int = MINIMUM_CPU_COUNT + 1
        private val MAXIMUM_POOL_SIZE: Int = MINIMUM_CPU_COUNT * 2 + 1
        private val sThreadFactory: ThreadFactory = object : ThreadFactory {
            private val mCount: AtomicInteger = AtomicInteger(1)
            public override fun newThread(r: Runnable): Thread {
                val t: Thread = Thread(r, "AsyncTask #" + mCount.getAndIncrement())
                t.priority = Process.THREAD_PRIORITY_BACKGROUND
                return t
            }
        }
        val sThreadPool: ThreadPoolUtils = ThreadPoolUtils(CORE_POOL_SIZE, sThreadFactory)
    }
}