package com.example.koin.di

import com.example.koin.data.network.ApiGenerator
import com.example.koin.data.network.service.ServiceApi
import com.example.koin.data.network.service.iServiceApi
import com.example.koin.data.storage.realm.IDataDao
import com.example.koin.data.storage.realm.DataDao
import com.example.koin.repository.DataRepository
import com.example.koin.repository.IDataRepository
import com.example.koin.utils.SharedPrefs
import com.google.gson.Gson
import org.koin.dsl.module

val resourceModule = module {
    single { Gson() }
    single { SharedPrefs(get()) }
}

val dbModule = module {
    single<IDataDao> { DataDao() }
}

val retrofitModule = module {
    single<iServiceApi> { ServiceApi() }
    single { ApiGenerator(get()) }
}

val repositoryModule = module {
    single<IDataRepository> { DataRepository(get(), get()) }
}
