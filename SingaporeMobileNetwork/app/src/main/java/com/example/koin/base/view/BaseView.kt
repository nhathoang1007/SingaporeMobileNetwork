package com.example.koin.base.view

import android.content.Context
import com.example.koin.base.BaseViewModel
import com.example.koin.base.viewmodel.DataState

interface BaseView<VM: BaseViewModel> {
    val mContext: Context
    val mViewModel: VM
    fun handelError(error: DataState.Failure)
    fun handelLoading(state: DataState.Loading)
}