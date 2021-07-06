package com.example.coroutines.model

import com.example.coroutines.extensions.getDefault
import com.google.gson.annotations.SerializedName

class Response constructor(
    val help: String,
    val success: Boolean,
    val result: Result
)

class Result constructor(
    @SerializedName("resource_id")
    val resourceId: String?,
    val records: MutableList<Record>?
) {
    fun getChartData(): Pair<MutableList<Float>, MutableList<String>> {
        val list = mutableListOf<Float>()
        val quarters = mutableListOf<String>()
        records?.filter { it.getYear() in 2008..2018 }?.forEach { record ->
            var value: Float? = null
            kotlin.runCatching {
                value = record.volumeOfMobileData?.toFloat()
            }
            value?.let {
                list.add(it)
                quarters.add(record.quarter.getDefault())
            }
        }
        return Pair(list, quarters)
    }
}


class Record constructor(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("volume_of_mobile_data")
    val volumeOfMobileData: String?,
    val quarter: String?
) {
    fun getYear(): Int {
        val value = quarter?.split("-")
        if (value?.size == 2) {
            kotlin.runCatching {
                return value[0].toInt()
            }
        }
        return 0
    }
}