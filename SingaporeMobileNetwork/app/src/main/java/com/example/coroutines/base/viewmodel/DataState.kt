package com.example.coroutines.base.viewmodel

sealed class DataState {
    data class Single<T>(val data: T) : DataState()
    data class List<T>(val data: MutableList<T>) : DataState()
    data class Failure(val message: String? = null, val t: Throwable? = null) : DataState()
    data class Loading(val isLoading: Boolean) : DataState()
}