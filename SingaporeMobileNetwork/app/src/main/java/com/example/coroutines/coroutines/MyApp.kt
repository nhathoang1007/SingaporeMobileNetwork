package com.example.coroutines.coroutines

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.coroutines.di.*
import com.example.coroutines.extensions.getDefault
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import java.util.concurrent.atomic.AtomicBoolean

open class MyApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        MultiDex.install(this)
        config()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApp)
            androidFileProperties()
            modules(
                listOf(
                    resourceModule,
                    dbModule,
                    retrofitModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }

    open fun config() {
        if (!isRunningTest()) {
            Realm.init(this)
        } else {
            stopKoin()
        }
    }

    private var isRunningTest: AtomicBoolean? = null

    @Synchronized
    open fun isRunningTest(): Boolean {
        if (null == isRunningTest) {
            val isTest: Boolean = try {
                Class.forName("androidx.test.espresso.Espresso")
                true
            } catch (e: ClassNotFoundException) {
                false
            }
            isRunningTest = AtomicBoolean(isTest)
        }
        return isRunningTest?.get().getDefault()
    }


    companion object {
        lateinit var instance: Application

        fun getApplicationContext(): Context = instance.applicationContext
    }
}