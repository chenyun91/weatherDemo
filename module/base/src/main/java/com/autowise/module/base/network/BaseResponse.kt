package com.autowise.module.base.network

import androidx.annotation.Keep

/**
 * User: chenyun
 * Date: 2021/10/13
 * Description:
 * FIXME
 */
@Keep
class BaseResponse<T> {
    var code: Int? = null
    var status: String? = null
    var message: String? = null
    var data: T? = null

}

class ErrorResponse {
    var resultCode: String? = null
    var status: String? = null
    var message: String? = null
}


