package com.example.coroutines.base

import androidx.test.espresso.idling.CountingIdlingResource

object EspressoCountingIdlingResource {
    var idlingResource: CountingIdlingResource = CountingIdlingResource("loadingDialog")
}