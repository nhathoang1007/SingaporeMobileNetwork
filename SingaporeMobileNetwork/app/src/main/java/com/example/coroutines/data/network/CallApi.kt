package com.example.coroutines.data.network

import com.example.coroutines.model.Response
import com.example.coroutines.model.WorkoutResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface CallApi {
    @GET("datastore_search")
    fun getDataSource(
        @Query("resource_id") resourceid: String = "a807b7ab-6cad-4aa6-87d0-e283a7353a0f",
        @Query("limit") limit: Int = 100
    ): Observable<Response>
}
