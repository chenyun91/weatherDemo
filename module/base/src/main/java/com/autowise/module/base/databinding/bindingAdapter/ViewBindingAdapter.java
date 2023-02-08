package com.autowise.module.base.databinding.bindingAdapter;


import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.autowise.module.base.AppContext;
import com.autowise.module.base.R;
import com.autowise.module.base.anim.AnimUtils;
import com.autowise.module.base.common.utils.DeviceUtils;
import com.autowise.module.base.common.utils.ToastUtils;
import com.autowise.module.base.common.utils.UiUtils;
import com.autowise.module.view.ext.AnimExt;
import com.autowise.module.view.ext.ViewExt;

import androidx.annotation.ColorInt;
import androidx.databinding.BindingAdapter;

/**
 * User: chenyun
 * Date: 2021/11/22
 * Description:
 * FIXME
 */
public class ViewBindingAdapter {

    @BindingAdapter("isSelected")
    public static void setSelect(View view, boolean isSelected) {
        view.setSelected(isSelected);
    }

    @BindingAdapter("isEnable")
    public static void setEnable(View view, boolean isEnable) {
        view.setEnabled(isEnable);
    }

    @BindingAdapter("visible")
    public static void setVisible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("inVisible")
    public static void setInVisible(View view, boolean inVisible) {
        view.setVisibility(inVisible ? View.INVISIBLE : View.VISIBLE);
    }

    @BindingAdapter("shakeView")
    public static void shakeView(View view, boolean shake) {
        if (shake) {
            DeviceUtils.vibrate();
            AnimExt.INSTANCE.shake(view);
        }
    }

    //    @BindingAdapter("inVisible")
//    public static void setInVisible(View view, boolean inVisible) {
//        view.setVisibility(inVisible ? View.INVISIBLE : View.VISIBLE);
//    }
    @BindingAdapter("setViewBg")
    public static void setViewBg(View view, @ColorInt int strokeColor) {
        ViewExt.INSTANCE.setGradientDrawable(view, -1, UiUtils.INSTANCE.dp2px(AppContext.app, 1f), strokeColor, 0f, 0f);
    }

    @BindingAdapter("setRotateBySelfAnim")
    public static void setRotateBySelfAnim(View view, boolean isStart) {
        if (isStart) {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_rotate_by_self);
            view.startAnimation(animation);
        } else {
            view.clearAnimation();
        }
    }


}
