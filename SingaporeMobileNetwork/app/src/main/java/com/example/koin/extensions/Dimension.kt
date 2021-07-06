package com.example.koin.extensions

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by Nhat Vo on 14/06/2021.
 */
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

val Int.sp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Int.convertDpToPx(): Int {
    val displayMetrics = Resources.getSystem().displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), displayMetrics)
        .toInt()
}

fun Float.convertPxToDp(): Float {
    val displayMetrics = Resources.getSystem().displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, displayMetrics)
}