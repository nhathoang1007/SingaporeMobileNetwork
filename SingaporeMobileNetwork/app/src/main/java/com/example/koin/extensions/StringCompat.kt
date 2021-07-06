package com.example.koin.extensions

/**
 * Created by Nhat Vo on 14/06/2021.
 */

fun String.formatString(vararg args: Any?): String {
    return String.format(this, *args)
}

fun Int.formatString(vararg args: Any?): String {
    return String.format(this.getString(), *args)
}