package com.example.basesetupforandroid.util.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VB : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int,
) : RecyclerView.Adapter<BaseAdapter.ViewHolder<T, VB>>() {
    private var dataList: List<T> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T, VB> {
        val binding = inflateBinding(parent, layoutResId)
        return createViewHolder(binding)
    }

    abstract fun createViewHolder(binding: VB): ViewHolder<T, VB>

    override fun onBindViewHolder(holder: ViewHolder<T, VB>, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = dataList.size

    abstract class ViewHolder<T, VB : ViewDataBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root) {
        abstract fun bind(item: T)
    }

    fun setData(dataList: List<T>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    private fun inflateBinding(parent: ViewGroup, @LayoutRes layoutResId: Int): VB {
        val inflater = LayoutInflater.from(parent.context)
        return DataBindingUtil.inflate(inflater, layoutResId, parent, false)
    }
}

