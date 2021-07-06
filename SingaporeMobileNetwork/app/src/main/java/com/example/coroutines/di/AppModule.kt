package com.example.coroutines.di

import com.example.coroutines.data.network.ApiGenerator
import com.example.coroutines.data.network.service.ServiceApi
import com.example.coroutines.data.network.service.iServiceApi
import com.example.coroutines.data.storage.realm.IDataDao
import com.example.coroutines.data.storage.realm.DataDao
import com.example.coroutines.repository.DataRepository
import com.example.coroutines.repository.IDataRepository
import com.example.coroutines.utils.SharedPrefs
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
