package com.example.coroutines.view.test

import com.example.coroutines.R
import com.example.coroutines.databinding.ViewDailyWorkoutBinding
import com.example.coroutines.model.Data

/**
 * Created by Nhat Vo on 14/06/2021.
 */
class WorkoutAdapter(private val onMarkChanged: (Data) -> Unit) : com.example.coroutines.base.adapter.BaseBindingAdapter<ViewDailyWorkoutBinding, Data>() {
    override fun getLayoutId(viewType: Int): Int = R.layout.view_daily_workout

    override fun bindViewHolder(dataBinding: ViewDailyWorkoutBinding, position: Int) {
        val item = list[position]
        dataBinding.apply {
            data = item
            rvAssignment.adapter = AssignmentAdapter(item, onMarkChanged).apply {
                updateData(item.assignments)
            }
            executePendingBindings()
        }
    }

    override fun updateData(list: MutableList<Data>) {
        val isFirstInit = this.list.isEmpty()
        this.list.clear()
        this.list.addAll(list)
        if (isFirstInit) {
            notifyDataSetChanged()
        } else {
            notifyItemRangeChanged(0, this.list.size)
        }
    }
}