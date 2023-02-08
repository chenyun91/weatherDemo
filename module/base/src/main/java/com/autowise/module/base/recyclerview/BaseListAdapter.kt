package com.autowise.module.base.recyclerview

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * User: chenyun
 * Date: 2022/8/22
 * Description:
 * FIXME
 */
abstract class BaseListAdapter<T> :
    RecyclerView.Adapter<BaseListAdapter<T>.ViewHolder>() {

    val mList = mutableListOf<T>()

    fun setData(list: List<T>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun addList(list: List<T>) {
        mList.addAll(list)
//        notifyItemRangeChanged(mList.size - list.size - 1, list.size)
        notifyDataSetChanged()
    }


    inner class ViewHolder constructor(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int, entity: T) {
            bindData(position, entity, binding)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(getViewDataBinding(parent, viewType))
    }

    override fun onBindViewHolder(holder: BaseListAdapter<T>.ViewHolder, position: Int) {
        holder.bind(position, mList[position])
    }

    override fun getItemCount() = mList.size

    abstract fun getViewDataBinding(parent: ViewGroup, viewType: Int): ViewDataBinding

    abstract fun bindData(position: Int, entity: T, binding: ViewDataBinding)

}