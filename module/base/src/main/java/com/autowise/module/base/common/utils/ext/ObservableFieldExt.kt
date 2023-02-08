package com.autowise.module.base.common.utils.ext

import androidx.databinding.BaseObservable
import androidx.databinding.Observable

/**
 * User: chenyun
 * Date: 2022/11/29
 * Description:
 * FIXME
 */
object ObservableFieldExt {

    fun BaseObservable.addCallback(action: () -> Unit) {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                action()
            }
        })
    }

}