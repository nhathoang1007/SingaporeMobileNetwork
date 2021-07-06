package com.example.coroutines.di

import com.example.coroutines.data.network.ApiGenerator
import com.example.coroutines.data.network.service.ServiceApi
import com.example.coroutines.data.network.service.iServiceApi
import com.example.coroutines.data.storage.realm.IWorkoutDao
import com.example.coroutines.data.storage.realm.WorkoutDao
import com.example.coroutines.repository.WorkoutRepository
import com.example.coroutines.utils.SharedPrefs
import com.google.gson.Gson
import org.koin.dsl.module

val resourceModule = module {
    single { Gson() }
    single { SharedPrefs(get()) }
}

val dbModule = module {
    single<IWorkoutDao> { WorkoutDao() }
}

val retrofitModule = module {
    single<iServiceApi> { ServiceApi() }
    single { ApiGenerator(get()) }
}

val repositoryModule = module {
    single { WorkoutRepository(get(), get()) }
}
