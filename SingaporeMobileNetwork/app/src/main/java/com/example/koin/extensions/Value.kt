package com.example.koin.extensions


/**
 * Created by Nhat.vo on 8/16/20.
 */

fun String?.getDefault(): String {
    return this ?: ""
}

fun String?.getDefaultDisplayValue(): String {
    return if (this.isNullOrEmpty()) {
        "-"
    } else {
        this
    }
}

fun Int?.getDefault(): Int {
    return this ?: 0
}

fun Int?.getDisplay(): String {
    return this?.toString() ?: "-"
}

fun Double?.getDefault(): Double {
    return this ?: 0.0
}

fun Long?.getDefault(): Long {
    return this ?: 0
}

fun Boolean?.getDefault(): Boolean {
    return this ?: false
}