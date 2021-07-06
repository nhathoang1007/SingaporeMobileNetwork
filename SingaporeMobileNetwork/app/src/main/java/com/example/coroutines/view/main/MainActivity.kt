package com.example.coroutines.view.main

import com.example.coroutines.R
import com.example.coroutines.base.viewmodel.DataState
import com.example.coroutines.databinding.TestBinding
import com.example.coroutines.utils.MyHandler
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class MainActivity : com.example.coroutines.base.view.BaseActivity<TestBinding, MainViewModel>() {

    override val mViewModel: MainViewModel by viewModel()

    override fun getLayoutRes(): Int = R.layout.test

    override fun onStart() {
        super.onStart()
        MyHandler().postDelayed({
            mViewModel.testPrefs()
        }, 1000)
    }

    override fun initView() {
        super.initView()
        dataBinding.apply {
            iVew = this@MainActivity
            /*edtEmail.doAfterTextChanged {
                 mViewModel.onEmailChanged(it.toString())
             }
             edtPassword.doAfterTextChanged {
                 mViewModel.onPasswordChanged(it.toString())
             }*/
        }
    }

    override fun handelError(error: DataState.Failure) {

    }
}