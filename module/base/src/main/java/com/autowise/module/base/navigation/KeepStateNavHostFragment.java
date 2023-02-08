package com.autowise.module.base.navigation;

import android.view.View;

import com.autowise.module.base.R;

import androidx.annotation.NonNull;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;

/**
 *  解决切换Fragment时，fragment会重建的问题。
 *
 *  参考：https://www.jianshu.com/p/2214d15fb824
 */
public class KeepStateNavHostFragment extends NavHostFragment {

    @NonNull
    @Override
    protected Navigator<? extends FragmentNavigator.Destination> createFragmentNavigator() {
        return new KeepStateFragmentNavigator(requireContext(), getChildFragmentManager(),
                getContainerId());
    }

    protected int getContainerId() {
        int id = getId();
        if (id != 0 && id != View.NO_ID) {
            return id;
        }
        // Fallback to using our own ID if this Fragment wasn't added via
        // add(containerViewId, Fragment)
        return R.id.nav_host_fragment_container;
    }
}