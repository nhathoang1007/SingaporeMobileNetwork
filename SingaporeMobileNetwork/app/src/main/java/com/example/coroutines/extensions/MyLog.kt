package com.example.coroutines.extensions

import android.util.Log


fun String.logError(message: String) {
    Log.e(this, message)
}