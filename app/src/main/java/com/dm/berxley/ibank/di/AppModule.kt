package com.dm.berxley.ibank.di

import androidx.room.Room
import com.dm.berxley.ibank.BuildConfig
import com.dm.berxley.ibank.auth_feature.presentatation.login.LoginViewModel
import com.dm.berxley.ibank.auth_feature.presentatation.register.RegisterViewModel
import com.dm.berxley.ibank.core.data.local.BankDatabase
import com.dm.berxley.ibank.core.data.remote.BankApi
import com.dm.berxley.ibank.core.presentation.home.HomeViewModel
import com.dm.berxley.ibank.core.presentation.main.MainViewModel
import com.dm.berxley.ibank.core.presentation.util.FirebaseAuthHelper
import com.dm.berxley.ibank.settings_feature.presentation.settings_main_screen.SettingsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(BankApi::class.java)
    }

    single {
        Room.databaseBuilder(
            context = androidContext(),
            name = BankDatabase.ROOM_DB_NAME,
            klass = BankDatabase::class.java
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single { get<BankDatabase>().dao }

    single { FirebaseAuthHelper(androidContext()) }

    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::SettingsViewModel)

}