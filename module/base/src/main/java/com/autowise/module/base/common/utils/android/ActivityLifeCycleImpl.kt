package com.autowise.module.base.common.utils.android

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.autowise.module.base.track.*
import org.json.JSONObject
import java.util.*

/**
 * User: chenyun
 * Date: 2022/8/8
 * Description:
 * FIXME
 */
class ActivityLifeCycleImpl : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        ActivityStack.getInstance().pushActivity(activity)
        TrackDataBaseUtils.addNewData(
            TrackEntity(
                EventType.PAGE,
                EventID.PAGE_IN,
                activity::class.simpleName.toString(),
                ""
            )
        )
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        ActivityStack.getInstance().popActivity(activity)
        TrackDataBaseUtils.addNewData(
            TrackEntity(
                EventType.PAGE,
                EventID.PAGE_OUT,
                activity::class.simpleName.toString(),
                ""
            )
        )
    }
}