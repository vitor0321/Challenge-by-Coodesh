package com.example.pharmainc

import android.app.Application
import com.example.pharmainc.di.appModules
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

            modules(appModules)
        }
    }
}