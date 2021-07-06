package com.example.coroutines.coroutines

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.example.coroutines.di.*
import com.example.coroutines.di.retrofitModule
import io.realm.Realm
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : MultiDexApplication() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
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
        Realm.init(this)
    }

    companion object {
        lateinit var instance: MyApp

        fun getApplicationContext(): Context = instance.applicationContext
    }
}