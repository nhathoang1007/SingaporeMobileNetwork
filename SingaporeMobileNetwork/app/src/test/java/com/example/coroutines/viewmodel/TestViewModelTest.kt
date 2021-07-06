package com.example.coroutines.viewmodel

import android.os.SystemClock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.coroutines.R
import com.example.coroutines.base.viewmodel.DataState
import com.example.coroutines.model.Record
import com.example.coroutines.model.Result
import com.example.coroutines.view.TestViewModel
import com.google.gson.JsonSyntaxException
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.`should equal`
import org.amshove.kluent.`should not be`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.model.Statement
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection
import com.example.coroutines.coroutines.MyApp
import com.example.coroutines.di.*
import com.example.coroutines.repository.IDataRepository
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing
import kotlin.random.Random

@RunWith(AndroidJUnit4::class)
class TestViewModelTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    private var dataRepository: IDataRepository = mock()
    private lateinit var mViewModel: TestViewModel

    @Before
    fun init() {
        mViewModel = TestViewModel(dataRepository)
        whenever(dataRepository.loadDataFromServer())
            .thenAnswer {
                return@thenAnswer Observable.just(createFakeResults())
            }

        whenever(dataRepository.loadDataFromLocalStorage()).thenAnswer {
            return@thenAnswer Observable.just(Result(records = mutableListOf()))
        }
    }

    private fun createFakeResults(): Result {
        val fakeData = mutableListOf<Record>()
        for (i in 2008..2018) {
            for (j in 1..4) {
                fakeData.add(
                    Record(
                        id = i.toString(),
                        volumeOfMobileData = Random.nextDouble(0.0, 30.0).toString(),
                        quarter = "$i-Q$j"
                    )
                )
            }
        }
        return Result(records = fakeData)
    }

    @Test
    fun `Test Null`() {
        mViewModel.dataObs `should not be` null
        mViewModel.stateObs `should not be` null
    }

    @Test
    fun `Test Load Data From Local Store Without Any Exception`() {
        whenever(dataRepository.loadDataFromLocalStorage()).thenReturn(
            Observable.error(
                UnknownHostException()
            )
        )
        mViewModel.getData()
        mViewModel.dataObs.value?.records `should not be` null
    }

    @Test
    fun `Test Http Errors`() {
        stubHttpError(HttpsURLConnection.HTTP_UNAUTHORIZED)
        mViewModel.getData()
        (mViewModel.stateObs.value as DataState.Failure).getErrorMessage() `should equal` MyApp.instance.getString(R.string.unauthorized_user)

        stubHttpError(HttpsURLConnection.HTTP_FORBIDDEN)
        mViewModel.getData()
        (mViewModel.stateObs.value as DataState.Failure).getErrorMessage() `should equal` MyApp.instance.getString(R.string.forbidden)

        stubHttpError(HttpsURLConnection.HTTP_INTERNAL_ERROR)
        mViewModel.getData()
        (mViewModel.stateObs.value as DataState.Failure).getErrorMessage() `should equal` MyApp.instance.getString(R.string.internal_server_error)

        stubHttpError(HttpsURLConnection.HTTP_BAD_REQUEST)
        mViewModel.getData()
        (mViewModel.stateObs.value as DataState.Failure).getErrorMessage() `should equal` MyApp.instance.getString(R.string.bad_request)
    }

    @Test
    fun `Test Json Format Error`() {
        whenever(dataRepository.loadDataFromServer()).thenReturn(
            Observable.error(
                JsonSyntaxException("")
            )
        )
        mViewModel.getData()
        SystemClock.sleep(5000)
        (mViewModel.stateObs.value as DataState.Failure).getErrorMessage() `should equal` MyApp.instance.getString(R.string.wrong_data_format)
    }

    @Test
    fun `Test Network Error`() {
        whenever(dataRepository.loadDataFromServer()).thenReturn(
            Observable.error(
                UnknownHostException()
            )
        )
        mViewModel.getData()
        SystemClock.sleep(5000)
        (mViewModel.stateObs.value as DataState.Failure).getErrorMessage() `should equal` MyApp.instance.getString(R.string.no_internet)
    }

    @After
    fun reset() {
        Mockito.reset(dataRepository)
    }

    private fun stubHttpError(code: Int) {
        val dump = "application/json"
        whenever(dataRepository.loadDataFromServer()).thenReturn(
            Observable.error(
                HttpException(
                    Response.error<ResponseBody>(
                        code,
                        dump.toResponseBody()
                    )
                )
            )
        )
    }

    class RxImmediateSchedulerRule : TestRule {

        override fun apply(base: Statement, d: Description): Statement {
            return object : Statement() {
                @Throws(Throwable::class)
                override fun evaluate() {
                    RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                    RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
                    RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
                    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

                    try {
                        base.evaluate()
                    } finally {
                        RxJavaPlugins.reset()
                        RxAndroidPlugins.reset()
                    }
                }
            }
        }
    }
}


fun <T> whenever(methodCall: T): OngoingStubbing<T> = Mockito.`when`(methodCall)!!
inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)!!