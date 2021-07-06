package com.example.coroutines.extensions

import android.text.format.DateUtils
import com.example.coroutines.utils.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Nhat Vo on 19/11/2020.
 */

fun String?.timeFormat(dateTimeFormat: String?): String {
    kotlin.runCatching {
        var timeFormat = dateTimeFormat
        if (timeFormat.isNullOrEmpty()) {
            timeFormat = "dd MMM yyyy"
        }
        val millis =
            this.convertStringToMillis(Constants.PATTERN_PARSE_TIME_SERVER, isApplyTimezone = false)
        return if (millis != 0.toLong()) {
            val cal = Calendar.getInstance()
            cal.timeInMillis = millis
            val format = SimpleDateFormat(timeFormat, Locale.ENGLISH)
            format.format(cal.time)
        } else {
            "-"
        }
    }
    return "-"
}

fun String.getRealDate() {

}

fun Long?.timeFormat(
    dateTimeFormat: String
): String {
    try {
        return if (this != 0.toLong()) {
            val cal = Calendar.getInstance()
            cal.timeInMillis = this.getDefault()
            val format = SimpleDateFormat(dateTimeFormat, Locale.ENGLISH)
            format.timeZone = TimeZone.getTimeZone("UTC")
            format.format(cal.time)
        } else {
            "-"
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return "-"
}

fun String?.isToday(): Boolean {
    this.convertStringToMillis(Constants.PATTERN_PARSE_TIME_SERVER, isApplyTimezone = false).apply {
        return DateUtils.isToday(this)
    }
}

fun String?.isPast(): Boolean {
    this.convertStringToMillis(Constants.PATTERN_PARSE_TIME_SERVER, isApplyTimezone = false).apply {
        return !DateUtils.isToday(this) && Calendar.getInstance().timeInMillis > this
    }
}

fun String?.convertStringToMillis(
    format: String = Constants.PATTERN_PARSE_TIME_SERVER,
    isApplyTimezone: Boolean = true
): Long {
    if (this.isNullOrEmpty()) {
        return 0
    }
    val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
    if (isApplyTimezone) {
        simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
    }

    return try {
        simpleDateFormat.parse(this)?.time ?: 0
    } catch (ignored: ParseException) {
        0
    }
}