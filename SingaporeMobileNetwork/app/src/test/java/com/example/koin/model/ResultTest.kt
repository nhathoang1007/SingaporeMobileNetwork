package com.example.koin.model

import org.amshove.kluent.`should equal`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import kotlin.random.Random

@RunWith(JUnit4::class)
class ResultTest {
    @Mock
    private lateinit var result: Result

    @Before
    fun setup() {
        result = createFakeResults()
    }

    @Test
    fun `Test Expected Record`() {
        result.getChartData().first.size `should equal` (2018 - 2008 + 1) * 4
    }

    @Test
    fun `Test Parse Quarter`() {
        result.records?.get(0)?.getYear() `should equal` 2000
    }

    private fun createFakeResults(): Result {
        val fakeData = mutableListOf<Record>()
        for (i in 2000..2021) {
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

}