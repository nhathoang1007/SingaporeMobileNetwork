package com.example.koin.utils

import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.koin.extensions.getDefault
import com.example.koin.extensions.isEnable
import io.reactivex.subjects.PublishSubject

object BindingUtils {

    @BindingAdapter("app:isVisible")
    @JvmStatic
    fun View.isVisible(isVisible: Boolean?) {
        visibility = if (isVisible.getDefault()) View.VISIBLE else View.GONE
    }

    @BindingAdapter("app:isSelected")
    @JvmStatic
    fun View.isSelected(isSelected: Boolean?) {
        this.isSelected = isSelected ?: false
    }

    @BindingAdapter("app:bindTo")
    @JvmStatic
    fun AppCompatButton.bindTo(subject: PublishSubject<Boolean>) {
        subject.subscribe(this.isEnable())
    }

    /*Recycler View*/
    @BindingAdapter("app:initLinear")
    @JvmStatic
    fun RecyclerView.initLinear(type: Int = RecyclerView.HORIZONTAL) {
        this.layoutManager = LinearLayoutManager(this.context, type, false)
    }

    @BindingAdapter("app:bindAdapter")
    @JvmStatic
    fun RecyclerView.bindAdapter(adapter: RecyclerView.Adapter<*>) {
        this.adapter = adapter
    }
}