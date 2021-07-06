package com.example.koin.extensions

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.koin.R
import com.example.koin.customize.DividerWithoutLastItemDecorator

/**
 * Created by Nhat Vo on 14/06/2021.
 */


fun RecyclerView.addDivider(padding: Int = 0) {
    val dividerDrawable = ContextCompat.getDrawable(context, R.drawable.divider)
    dividerDrawable?.let {
        this.addItemDecoration(
            DividerWithoutLastItemDecorator(it, padding.convertDpToPx())
        )
    }
}