package com.example.coroutines.customize.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.StyleableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding

/**
 * Created by Nhat.vo on 18/11/2019.
 */

abstract class BaseCustomView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.initViewDataBinging(inflater)
        onSetupView(attrs)
    }

    private fun onSetupView(attrs: AttributeSet?) {
        onCreatedView()
        try {
            if (attrs != null && getStyleable() != null) {
                val styleable = context.obtainStyledAttributes(attrs, getStyleable()!!, 0, 0)
                applyStyleable(styleable)
                styleable.recycle()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    abstract fun initViewDataBinging(inflater: LayoutInflater): ViewDataBinding

    @StyleableRes
    open fun getStyleable(): IntArray? = null

    open fun onCreatedView() {}

    @SuppressLint("Recycle")
    open fun applyStyleable(styleable: TypedArray) {
    }
}