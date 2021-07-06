package com.example.koin.base.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.koin.base.BaseViewModel
import com.example.koin.coroutines.MyApp
import com.example.koin.base.viewmodel.DataState
import com.example.koin.customize.dialog.LoadingDialog
import com.example.koin.extensions.logError
import org.koin.core.context.KoinContextHandler

abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(), BaseView<VM> {

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
        error.getErrorMessage()?.let {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
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

    override fun onDestroy() {
        super.onDestroy()
        KoinContextHandler.stop()
    }
}