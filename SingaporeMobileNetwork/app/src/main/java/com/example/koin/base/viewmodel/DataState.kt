package com.example.koin.base.viewmodel

import com.example.koin.R
import com.example.koin.extensions.getString
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

sealed class DataState {
    data class Single<T>(val data: T) : DataState()
    data class List<T>(val data: MutableList<T>) : DataState()
    data class Failure(val message: String? = null, val t: Throwable? = null) : DataState() {
        fun getErrorMessage(): String? {
            return if (!message.isNullOrEmpty()) {
                message
            } else {
                when(t) {
                    is HttpException -> {
                        when (t.code()) {
                            HttpsURLConnection.HTTP_UNAUTHORIZED -> R.string.unauthorized_user.getString()
                            HttpsURLConnection.HTTP_FORBIDDEN -> R.string.forbidden.getString()
                            HttpsURLConnection.HTTP_INTERNAL_ERROR -> R.string.internal_server_error.getString()
                            HttpsURLConnection.HTTP_BAD_REQUEST -> R.string.bad_request.getString()
                            else -> t.getLocalizedMessage()
                        }
                    }
                    is JsonSyntaxException -> {
                        R.string.wrong_data_format.getString()
                    }
                    is UnknownHostException -> {
                        R.string.no_internet.getString()
                    }
                    else -> {
                        t?.message
                    }
                }
            }
        }
    }
    data class Loading(val isLoading: Boolean) : DataState()
}