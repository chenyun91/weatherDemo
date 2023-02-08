package com.autowise.module.base.recyclerview

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.autowise.module.base.BaseFragment
import com.autowise.module.base.R
import com.autowise.module.base.databinding.BaseListLayoutBinding

/**
 * User: chenyun
 * Date: 2022/8/22
 * Description:
 *  支持上拉、下拉的list
 * FIXME
 */
abstract class BaseListFragment : BaseFragment<BaseListLayoutBinding>() {

    lateinit var refreshHelper: SwipeRefreshHelper

    override fun getLayoutId() = R.layout.base_list_layout

    /**
     * 加载更多的数据
     * @return 失败，返回false，成功返回true
     */
    fun setLoadMoreData(entity: BaseListEntity<*>?): Boolean {
        if (entity == null) { //请求失败
            refreshHelper.loadMoreFail()
            return false
        }
        refreshHelper.loadMoreComplete(entity.hasMore)
        refreshHelper.isLoadMoreEnable = entity.hasMore
        if (!entity.hasMore) {
            refreshHelper.setNoMoreData()
        }
//        mAdapter.addList(entity.list)
        return true
    }

    /**
     * 加载刷新的数据
     * @return 失败，返回false，成功返回true
     */
    fun setRefreshData(entity: BaseListEntity<*>?): Boolean {
        refreshHelper.refreshComplete()
        refreshHelper.isLoadMoreEnable = entity?.hasMore ?: false

        if (entity == null) {
            if (getAdapter().itemCount > 0) {//已经有数据了
                return false
            }
            showError()
            return false
        }
        if (entity.list.isEmpty()) {
            showNoData()
        } else {
            showNormal()
        }
//        mAdapter.setData(entity.list)
        return true
    }

    @CallSuper
    override fun initView() {
        setContainerView(v.clContent)
        v.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter =
                RecyclerAdapterWithHF(this@BaseListFragment.getAdapter())
        }
        v.titleLayout.onBackClickListener = {
            onBackPressed()
        }
        initSwipeRefreshHelper()
    }

    private fun initSwipeRefreshHelper() {
        refreshHelper = SwipeRefreshHelper(v.swlRefresh)
        //下拉刷新
        refreshHelper.onRefreshListener = {
            refreshData()
        }
        //拉到底分页数据
        refreshHelper.setOnLoadMoreListener {
            loadMoreDate()
        }
    }

    /**
     * 加载更多
     */
    open fun loadMoreDate() {

    }

    abstract fun getAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>

}