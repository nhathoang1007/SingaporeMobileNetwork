package com.example.koin.di

import com.example.koin.view.TestViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TestViewModel(get()) }
}