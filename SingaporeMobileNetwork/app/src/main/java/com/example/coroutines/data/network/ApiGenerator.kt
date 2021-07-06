package com.example.coroutines.data.network

import com.example.coroutines.data.network.service.iServiceApi

class ApiGenerator(val service: iServiceApi) {
    fun createApi(): CallApi {
        return service.createService(CallApi::class.java)
    }

    fun createTokenApi(): CallApi {
        return service.createServiceToken(CallApi::class.java)
    }
}