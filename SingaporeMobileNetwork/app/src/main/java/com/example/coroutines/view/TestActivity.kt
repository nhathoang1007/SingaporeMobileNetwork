package com.example.coroutines.view

import com.example.coroutines.R
import com.example.coroutines.base.view.BaseActivity
import com.example.coroutines.base.viewmodel.DataState
import com.example.coroutines.databinding.ActivityTestBinding
import com.example.coroutines.utils.BindingUtils.isVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.ref.WeakReference

class TestActivity : BaseActivity<ActivityTestBinding, TestViewModel>(), TestView {

    override val mViewModel: TestViewModel by viewModel()

    override fun getLayoutRes(): Int = R.layout.activity_test

    override fun initView() {
        super.initView()
        instance = WeakReference(this)
        dataBinding.apply {
            iView = this@TestActivity
        }
    }

    companion object {
        private lateinit var instance: WeakReference<TestActivity>
        fun getInstance(): WeakReference<TestActivity> { // hacky way to get Main Activiy instance for test due to error of ScenarioActivity. Don't abuse this.
            return instance
        }
    }
}