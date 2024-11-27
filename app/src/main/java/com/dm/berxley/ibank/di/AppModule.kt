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
import com.dm.berxley.ibank.search_feature.data.repositories.CurrencyRepositoryImpl
import com.dm.berxley.ibank.search_feature.domain.repositories.CurrencyRepository
import com.dm.berxley.ibank.search_feature.presentation.search_exchange_rate.ExchangeRateViewModel
import com.dm.berxley.ibank.settings_feature.presentation.settings_main_screen.SettingsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
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
            .baseUrl(BuildConfig.CURRENCY_BASE_URL)
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

    singleOf(::CurrencyRepositoryImpl).bind<CurrencyRepository>()

    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::ExchangeRateViewModel)

}