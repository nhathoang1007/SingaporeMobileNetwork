package com.example.koin.data.network.service

interface iServiceApi {
    fun <S> createService(serviceClass: Class<S>): S
    fun <S> createServiceToken(serviceClass: Class<S>): S
}