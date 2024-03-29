package com.example.pharmainc.presentation

import android.app.Application
import com.example.pharmainc.presentation.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class PharmaIncApp : Application() {

    override fun onCreate() {
        super.onCreate()
        modulesKoin()
    }

    private fun modulesKoin() {
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@PharmaIncApp)

            modules(
                navModule,
                viewModelModule,
                uiModule,
                dataBindingModule,
                useCaseModule,
                daoModule
            )
        }
    }
}