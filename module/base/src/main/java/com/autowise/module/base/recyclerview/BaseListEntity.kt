package com.autowise.module.base.recyclerview


/**
 * User: chenyun
 * Date: 2022/8/22
 * Description:
 * FIXME
 */
data class BaseListEntity<T>(
    val hasMore: Boolean,
    val lastTimestamp: Long,
    val list: List<T>
)