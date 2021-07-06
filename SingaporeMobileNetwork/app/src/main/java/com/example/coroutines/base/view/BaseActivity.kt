package com.example.coroutines.base.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.coroutines.coroutines.MyApp
import com.example.coroutines.base.viewmodel.DataState
import com.example.coroutines.customize.dialog.LoadingDialog
import com.example.coroutines.extensions.logError

abstract class BaseActivity<V : ViewDataBinding, VM : com.example.coroutines.base.BaseViewModel> : AppCompatActivity(),
    com.example.coroutines.base.view.BaseView<VM> {

    protected val TAG = this::class.simpleName

    protected lateinit var dataBinding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.lifecycle.addObserver(mViewModel)
        dataBinding = DataBindingUtil.setContentView(this, getLayoutRes())
        dataBinding.lifecycleOwner = this
        initView()
        initViewModel()
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    open fun initView() {}

    open fun initViewModel() {
        mViewModel.apply {
            stateObs.observe(this@BaseActivity, Observer {
                when (it) {
                    is DataState.Loading -> handelLoading(it)
                    is DataState.Failure -> {
                        handelLoading(DataState.Loading(false))
                        handelError(it)
                    }
                    else -> {
                        Log.d(TAG, "State not detected!")
                    }
                }
            })
        }
    }

    override fun handelError(error: DataState.Failure) {
        Log.d(TAG, "TODO Handel error here!")
    }

    override fun handelLoading(state: DataState.Loading) {
        TAG?.logError("$state")
        if (state.isLoading) {
            LoadingDialog.show(this)
        } else {
            LoadingDialog.dismiss()
        }
    }

    override val mContext: Context
        get() = MyApp.getApplicationContext()
}