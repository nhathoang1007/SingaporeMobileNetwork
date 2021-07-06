package com.example.coroutines.view.test

import com.example.coroutines.base.view.BaseView

/**
 * Created by Nhat Vo on 14/06/2021.
 */
interface TestView: BaseView<TestViewModel> {
    val mAdapter: WorkoutAdapter
}