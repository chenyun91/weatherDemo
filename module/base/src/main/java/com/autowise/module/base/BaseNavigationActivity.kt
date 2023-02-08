package com.autowise.module.base

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.NavigationRes
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.autowise.module.base.databinding.BaseNavHostLayoutBinding
import com.autowise.module.base.navigation.KeepStateFragmentNavigator


/**
 * User: chenyun
 * Date: 2022/9/8
 * Description:
 * FIXME
 */

abstract class BaseNavigationActivity : BaseActivity<BaseNavHostLayoutBinding>() {


    override fun getLayoutId() = R.layout.base_nav_host_layout

    @CallSuper
    override fun initView() {
        setStartDestination()
    }

    abstract fun setStartDestination()

   protected fun setStartDestination(
        @NavigationRes graphResId: Int,
        startDestinationID: Int?,
        args: Bundle
    ) {
//        val navController =
//            (this.supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment).navController
//        val navController = Navigation.findNavController(this, R.id.fragmentContainer)
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

        navController.navigatorProvider.addNavigator(
            KeepStateFragmentNavigator(
                this,
                navHostFragment.childFragmentManager,
                R.id.fragmentContainer
            )
        )
        val navGraph = navController.navInflater.inflate(graphResId)
        startDestinationID?.apply {
            navGraph.startDestination = this
        }
        navController.setGraph(navGraph, args)
    }


    fun nav() {
//        val navController = Navigation.findNavController(this, R.id.fragmentContainer)
//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
//        navController.navigatorProvider.addNavigator(
//            KeepStateFragmentNavigator(
//                this,
//                navHostFragment.getChildFragmentManager(),
//                R.id.fragmentContainer
//            )
//        )
//        NavHostFragment.findNavController(navHostFragment).navigate(destinationID, bundle)
    }
}