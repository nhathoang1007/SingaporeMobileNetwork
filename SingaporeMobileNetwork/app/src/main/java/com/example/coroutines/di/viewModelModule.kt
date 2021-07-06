package com.example.coroutines.di

import com.example.coroutines.view.main.MainViewModel
import com.example.coroutines.view.test.TestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { TestViewModel(get()) }
}