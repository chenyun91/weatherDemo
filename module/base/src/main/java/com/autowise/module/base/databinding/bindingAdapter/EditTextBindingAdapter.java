package com.autowise.module.base.databinding.bindingAdapter;

import android.text.InputType;
import android.text.Selection;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

/**
 * User: chenyun
 * Date: 2021/11/22
 * Description:
 * FIXME
 */
public class EditTextBindingAdapter {

    @BindingAdapter("inputType")
    public static void setInputType(EditText et, String inputType) {
        if (inputType == null) {
            return;
        }
        switch (inputType) {
            case "textPassword":
                et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                et.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                break;
            case "textVisiblePassword":
                et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                et.setTransformationMethod(PasswordTransformationMethod.getInstance());
                break;
            case "text":
                et.setInputType(InputType.TYPE_CLASS_TEXT);
                break;

        }
        et.setSelection(et.length());
    }


}
