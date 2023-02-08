package com.autowise.module.base.recyclerview

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import androidx.recyclerview.widget.RecyclerView
import android.widget.GridView
import android.widget.AbsListView
import android.widget.ListView
import com.autowise.module.base.R
import java.lang.RuntimeException

class SwipeRefreshHelper(
    private val swipeRefreshLayout: SwipeRefreshLayout? = null,
    private val recyclerView: RecyclerView? = null
) {
    private var mContentView: View? = null
    private var mLoadMoreHandler: LoadMoreHandler? = null
    var isLoadingMore = false
        private set
    private var isAutoLoadMoreEnable = true
    private var hasInitLoadMoreView = false
    private var loadMoreViewFactory: ILoadMoreViewFactory? = DefaultLoadMoreViewFooter()
    private var mOnLoadMoreListener: OnLoadMoreListener? = null
    private var mLoadMoreView: ILoadMoreViewFactory.ILoadMoreView? = null
    var onRefreshListener: (() -> Unit)? = null
        set(value) {
            field = value
            swipeRefreshLayout?.setOnRefreshListener(mOnRefreshListener)
        }
    private val mOnRefreshListener = OnRefreshListener {
        onRefreshListener?.invoke()
    }

    var isLoadMoreEnable = false
        set(value) {
            if (field == value) {
                return
            }
            field = value
            if (!hasInitLoadMoreView && value) {
                mLoadMoreView = loadMoreViewFactory!!.madeLoadMoreView()
                if (null == mLoadMoreHandler) {
                    if (mContentView is GridView) {
                        mLoadMoreHandler = GridViewHandler()
                    } else if (mContentView is AbsListView) {
                        mLoadMoreHandler = ListViewHandler()
                    } else if (mContentView is RecyclerView) {
                        mLoadMoreHandler = RecyclerViewHandler()
                    }
                }
                checkNotNull(mLoadMoreHandler) { "unSupported contentView !" }
                hasInitLoadMoreView = mLoadMoreHandler!!.handleSetAdapter(
                    mContentView, mLoadMoreView,
                    onClickLoadMoreListener
                )
                mLoadMoreHandler!!.setOnScrollBottomListener(mContentView, onScrollBottomListener)
                return
            }
            if (hasInitLoadMoreView) {
                if (value) {
                    mLoadMoreHandler!!.addFooter()
                } else {
//                    mLoadMoreHandler!!.removeFooter()
                }
            }
        }

    /**
     * 不带下拉刷新
     */
    constructor(recyclerView: RecyclerView) : this(null, recyclerView)

    fun autoRefresh() {
        if (null != onRefreshListener) {
            swipeRefreshLayout?.isRefreshing = true
            onRefreshListener?.invoke()
        }
    }

    private fun init() {
        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.childCount <= 0) {
                throw RuntimeException("SwipeRefreshLayout has no child view")
            }
            for (index in 0 until swipeRefreshLayout.childCount) {
                val child = swipeRefreshLayout.getChildAt(index)
                if (child is RecyclerView || child is GridView || child is ListView) {
                    mContentView = child
                }
            }
            swipeRefreshLayout.setColorSchemeResources(R.color.base_swipe_refresh_color)
        } else {
            mContentView = recyclerView
        }
    }

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener?) {
        mOnLoadMoreListener = onLoadMoreListener
    }

    @Deprecated("", ReplaceWith("onRefreshListener = listener"))
    fun setOnSwipeRefreshListener(listener: (() -> Unit)) {
        onRefreshListener = listener
    }

    fun refreshComplete() {
        swipeRefreshLayout?.isRefreshing = false
        mLoadMoreView?.showNormal()
    }

    fun setFooterView(factory: ILoadMoreViewFactory?) {
        if (null == factory || null != loadMoreViewFactory && loadMoreViewFactory === factory) {
            return
        }
        loadMoreViewFactory = factory
        if (hasInitLoadMoreView) {
            mLoadMoreHandler!!.removeFooter()
            mLoadMoreView = loadMoreViewFactory!!.madeLoadMoreView()
            hasInitLoadMoreView = mLoadMoreHandler!!.handleSetAdapter(
                mContentView, mLoadMoreView,
                onClickLoadMoreListener
            )
            if (!isLoadMoreEnable) {
                mLoadMoreHandler!!.removeFooter()
            }
        }
    }

    fun setAutoLoadMoreEnable(isAutoLoadMoreEnable: Boolean) {
        this.isAutoLoadMoreEnable = isAutoLoadMoreEnable
    }

    private val onScrollBottomListener = OnScrollBottomListener {
        if (isAutoLoadMoreEnable && isLoadMoreEnable && !isLoadingMore && swipeRefreshLayout?.isRefreshing != true) {
            // can check network here
            loadMore()
        }
    }
    private val onClickLoadMoreListener = View.OnClickListener {
        if (isLoadMoreEnable && !isLoadingMore) {
            loadMore()
        }
    }

    private fun loadMore() {
        isLoadingMore = true
        mLoadMoreView!!.showLoading()
        if (null != mOnLoadMoreListener) {
            mOnLoadMoreListener!!.loadMore()
        }
    }

    fun loadMoreComplete(hasMore: Boolean) {
        isLoadingMore = false
        if (hasMore) {
            mLoadMoreView!!.showNormal()
        } else {
            setNoMoreData()
        }
    }

    fun setNoMoreData() {
        isLoadingMore = false
        mLoadMoreView!!.showNoMoreData()
    }

    fun loadMoreFail() {
        isLoadingMore = false
        mLoadMoreView!!.showFail()
    }

    init {
        init()
    }
}