package com.example.koin.ui

import android.os.SystemClock
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.koin.R
import com.example.koin.base.EspressoCountingIdlingResource
import com.example.koin.view.TestActivity
import com.google.gson.JsonSyntaxException
import org.hamcrest.Matchers
import org.junit.*
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class TestActivityTest {
    @JvmField
    @Rule
    val intentsTestRule = ActivityTestRule(TestActivity::class.java, true)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoCountingIdlingResource.idlingResource)
    }

    @Test
    fun errorToast_shouldBeShown() {
        ActivityScenario.launch(TestActivity::class.java)
        SystemClock.sleep(1000)
        TestActivity.getInstance().get()?.mViewModel?.setError(error = JsonSyntaxException(""))
        SystemClock.sleep(1000)
        Espresso.onView(withText(R.string.wrong_data_format)).inRoot(
            RootMatchers.withDecorView(
                Matchers.not(
                    TestActivity.getInstance().get()?.window?.decorView
                )
            )
        ).check(ViewAssertions.matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoCountingIdlingResource.idlingResource)
    }

}