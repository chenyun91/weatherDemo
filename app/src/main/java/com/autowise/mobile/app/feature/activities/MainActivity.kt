package com.autowise.mobile.app.feature.activities

import android.os.Bundle
import com.autowise.mobile.app.R
import com.autowise.module.base.BaseNavigationActivity

class MainActivity : BaseNavigationActivity() {

    override fun setStartDestination() {
        setStartDestination(R.navigation.nav_main, null, Bundle())
    }

}