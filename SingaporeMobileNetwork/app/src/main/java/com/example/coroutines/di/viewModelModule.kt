package com.example.coroutines.di

import com.example.coroutines.view.TestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TestViewModel(get()) }
}