package com.example.koin.view

import com.example.koin.R
import com.example.koin.base.view.BaseActivity
import com.example.koin.databinding.ActivityTestBinding
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