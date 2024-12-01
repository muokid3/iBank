package com.dm.berxley.ibank

import android.app.Application
import com.dm.berxley.ibank.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BankApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BankApp)
            modules(appModule)
        }
    }
}