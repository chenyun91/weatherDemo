package com.autowise.module.base.databinding.bindingAdapter;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

/**
 * User: chenyun
 * Date: 2021/11/22
 * Description:
 * FIXME
 */
public class TextViewBindingAdapter {


    @BindingAdapter(value = {"leftImg", "rightImg", "topImg", "bottomImg"}, requireAll = false)
    public static void setLeftOrRightImg(TextView tv, Drawable drawableLeft, Drawable drawableRight, Drawable drawableTop, Drawable drawableBottom) {
        if (drawableLeft != null) {
            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());//必须设置图片大小，否则不显示
        } else if (drawableRight != null) {
            drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());//必须设置图片大小，否则不显示
        } else if (drawableTop != null) {
            drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(), drawableTop.getMinimumHeight());//必须设置图片大小，否则不显示
        } else if (drawableBottom != null) {
            drawableBottom.setBounds(0, 0, drawableBottom.getMinimumWidth(), drawableBottom.getMinimumHeight());//必须设置图片大小，否则不显示
        } else {
            return;
        }

        tv.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
    }

//    @BindingAdapter("app:topImg")
//    public static void setTopImg(TextView tv, Drawable drawable) {
//        if (drawable != null) {
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
//        } else {
//            drawable = new BitmapDrawable();
//        }
//        tv.setCompoundDrawables(null, drawable, null, null);
//    }


   /* @BindingAdapter("app:onDrawableClickListener")
    public static void setDrawableClickListener(TextView tv, DrawableClickListener mListener) {
        int LEFT = 0;
        int RIGHT = 2;
        tv.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    if (mListener != null) {
                        Drawable drawableLeft = tv.getCompoundDrawables()[LEFT];
                        if (drawableLeft != null && event.getRawX() <= (tv.getLeft() + tv.getPaddingLeft() + drawableLeft.getBounds().width())) {
                            mListener.onLeft(v, drawableLeft);
                            return true;
                        }

                        Drawable drawableRight = tv.getCompoundDrawables()[RIGHT];
                        if (drawableRight != null && event.getRawX() >= (tv.getRight() - tv.getPaddingRight() - drawableRight.getBounds().width())) {
                            mListener.onRight(v, drawableRight);
                            return true;
                        }
                    }
                    break;
            }
            return false;
        });
    }*/

    public interface DrawableClickListener {
        public void onLeft(View v, Drawable left);

        public void onRight(View v, Drawable right);
    }

}