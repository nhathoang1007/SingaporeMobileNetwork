package com.example.koin.utils

open class EnumCompanion<T, V>(private val valueMap: Map<T, V>) {
    fun getByValue(type: T, default: V) = valueMap[type] ?: default
}