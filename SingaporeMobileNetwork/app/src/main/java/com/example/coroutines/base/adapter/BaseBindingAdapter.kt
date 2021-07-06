package com.example.coroutines.base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Nhat.vo on 17/11/2020.
 */
abstract class BaseBindingAdapter<V : ViewDataBinding, T>(val onClicked: ((T?) -> Unit)? = null) :
    RecyclerView.Adapter<com.example.coroutines.base.adapter.BaseViewHolder<V>>() {

    val list: MutableList<T> = mutableListOf()
    var parentWidth = 0
    lateinit var context: Context

    protected abstract fun getLayoutId(viewType: Int): Int
    abstract fun bindViewHolder(dataBinding: V, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.example.coroutines.base.adapter.BaseViewHolder<V> {
        parentWidth = parent.measuredWidth
        context = parent.context
        val binding = DataBindingUtil.inflate<V>(
            LayoutInflater.from(parent.context),
            getLayoutId(viewType),
            parent,
            false
        )

        return com.example.coroutines.base.adapter.BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: com.example.coroutines.base.adapter.BaseViewHolder<V>, position: Int) {
        bindViewHolder(holder.dataBinding, position)
    }

    open fun updateData(list: MutableList<T>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    open fun addData(data: T) {
        this.list.add(data)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size
}

open class BaseViewHolder<V : ViewDataBinding>(val dataBinding: V) :
    RecyclerView.ViewHolder(dataBinding.root) {
    open fun bind(position: Int) {}
}