package com.example.coroutines.view.test

import androidx.lifecycle.Observer
import com.example.coroutines.R
import com.example.coroutines.base.view.BaseActivity
import com.example.coroutines.databinding.ActivityTestBinding
import com.example.coroutines.extensions.addDivider
import com.example.coroutines.extensions.convertDpToPx
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestActivity : BaseActivity<ActivityTestBinding, TestViewModel>(), TestView {

    override val mAdapter: WorkoutAdapter by lazy {
        WorkoutAdapter { data ->
            mViewModel.onMarkAssignment(data)
        }
    }

    override val mViewModel: TestViewModel by viewModel()

    override fun getLayoutRes(): Int = R.layout.activity_test

    override fun initView() {
        super.initView()
        dataBinding.apply {
            iView = this@TestActivity
            rvWorkout.addDivider(1.convertDpToPx())
//            customLineChart.setData(listOf(10f, 15f, 20f, 30f, 28f, 22f, 30f, 35f, 37f, 39f, 100f))
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        mViewModel.apply {
            dataObs.observe(this@TestActivity, Observer {
                dataBinding.customLineChart.setData(it.getChartData())
            })
        }
    }
}