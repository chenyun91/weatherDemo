package com.autowise.module.base.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * User: chenyun
 * Date: 2022/5/16
 * Description:
 * FIXME
 */
@Parcelize
data class UserInfoBean(
    val chinese_name: String,
    val id: String,
    val is_developer: Boolean,
    val lang: String,
    val private_logo: String,
    val time_format: String,
    val time_zone: Int,
    val username: String,
    val email: String,
    var avatar: String
) : Parcelable

data class UserSigBean(
    val userSig: String
)
/*{"id":151,"username":"chenyun","chinese_name":"陈云","is_developer":true,"time_zone":0,"lang":"en-US","private_logo":"","time_format":"Y-m-d H:i:s","email":"chenyun@autowise.ai"},"message":""}*/