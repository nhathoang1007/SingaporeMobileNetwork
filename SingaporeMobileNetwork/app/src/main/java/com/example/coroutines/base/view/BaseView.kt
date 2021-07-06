package com.example.coroutines.base.view

import android.content.Context
import com.example.coroutines.base.BaseViewModel
import com.example.coroutines.base.viewmodel.DataState

interface BaseView<VM: BaseViewModel> {
    val mContext: Context
    val mViewModel: VM
    fun handelError(error: DataState.Failure)
    fun handelLoading(state: DataState.Loading)
}