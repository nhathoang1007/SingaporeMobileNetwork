package com.example.koin.base

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoCountingIdlingResource {
    var idlingResource: CountingIdlingResource = CountingIdlingResource("loadingDialog")
}