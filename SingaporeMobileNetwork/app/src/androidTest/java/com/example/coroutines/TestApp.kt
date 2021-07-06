package com.example.coroutines

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import androidx.test.runner.AndroidJUnitRunner
import com.example.coroutines.coroutines.MyApp
import com.example.coroutines.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class MockTestRunner : AndroidJUnitRunner() {
    override fun onCreate(arguments: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        super.onCreate(arguments)
    }

    override fun newApplication(cl: ClassLoader?, className: String?, context: Context?): Application {
        return super.newApplication(cl, MyApp::class.java.name, context)
    }
}