package com.example.coroutines.model

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorRes
import com.example.coroutines.R
import com.example.coroutines.extensions.*
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject

/**
 * Created by Nhat Vo on 14/06/2021.
 */

open class Assignment(
    var _id: String = "",
    var title: String = "",
    @SerializedName("total_exercise")
    var exerciseCount: Int = 0,
    var status: Int = 0,
    var isCompletedMarked: Boolean = false
) : RealmObject() {

    fun isCompletedTask(): Boolean =
        isCompletedMarked || Status.getByValue(status) == Status.COMPLETED

    fun getStatus(timestamp: String?): Pair<SpannableStringBuilder, Int> {
        val temps = R.string.exercises_size.getString().formatString(exerciseCount)
        val mStatus = Status.getByValue(status)
        return when {
            isCompletedMarked || mStatus == Status.COMPLETED -> {
                Pair(SpannableStringBuilder(R.string.completed.getString()), R.color.white)
            }
            timestamp.isPast() || mStatus == Status.MISSED -> {
                Pair(
                    getSpannable(R.string.missed, temps, R.color.color_red),
                    R.color.color_text_default
                )
            }
            timestamp.isToday() -> {
                Pair(
                    getSpannable(R.string.assigned, temps, R.color.color_blue),
                    R.color.color_text_default
                )
            }
            else -> {
                Pair(SpannableStringBuilder(temps), R.color.color_grey)
            }
        }
    }

    private fun getSpannable(idName: Int, temps: String, @ColorRes color: Int): SpannableStringBuilder {
        val text = idName.getString()
        val task = " • $temps"
        val span = SpannableStringBuilder(text.plus(task))
        span.setSpan(
            ForegroundColorSpan(color.getColor()),
            0,
            text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return span
    }
}

enum class Status(val status: Int) {
    TODO(0),
    ASSIGNED(1),
    COMPLETED(2),
    MISSED(3);

    companion object {
        private val values = values()
        fun getByValue(status: Int?) = values.firstOrNull { status == it.status } ?: TODO
    }
}