package com.example.koin.extensions

import android.content.Context
import androidx.core.content.ContextCompat
import com.example.koin.coroutines.MyApp

fun applicationContext(): Context {
    return MyApp.getApplicationContext()
}

fun Int.getColor(): Int {
    return ContextCompat.getColor(applicationContext(), this)
}


fun Int.getString(): String {
    return applicationContext().getString(this)
}
