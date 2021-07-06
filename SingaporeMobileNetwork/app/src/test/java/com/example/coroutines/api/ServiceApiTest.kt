package com.example.coroutines.api

import com.example.coroutines.data.network.CallApi
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source
import org.amshove.kluent.`should equal`
import org.amshove.kluent.`should not be`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class ServiceApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: CallApi

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(CallApi::class.java)
    }

    @Test
    fun `Test Server Get Data`() {
        val inputStream = javaClass.classLoader?.getResourceAsStream("data.json")
        val source = inputStream?.source()?.buffer()
        val mockResponse = MockResponse()
        if (source != null) {
            mockWebServer.enqueue(
                mockResponse.setBody(source.readString(Charsets.UTF_8))
            )
        }
        service.getDataSource()
            .test()
            .assertValue {
                val result = it.result.records
                !result.isNullOrEmpty() &&
                        result[0].id == "1"
                        && result[0].volumeOfMobileData == "0.000384"
                        && result[0].quarter == "2004-Q3"
            }
            .assertValue { it.result.records?.size == it.result.total }

        val request = mockWebServer.takeRequestWithTimeout()
        request?.requestUrl `should not be` null
        request?.requestUrl?.queryParameter("resource_id") `should equal` "a807b7ab-6cad-4aa6-87d0-e283a7353a0f"
        request?.requestUrl?.queryParameter("limit") `should equal` "100"
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

}

fun MockWebServer.takeRequestWithTimeout(timeout: Long = 5L): RecordedRequest? {
    return this.takeRequest(timeout, TimeUnit.SECONDS)
}