package com.raulastete.notemark.presentation

import android.app.Application
import com.raulastete.notemark.di.appModule
import com.raulastete.notemark.di.dataModule
import com.raulastete.notemark.di.homeModule
import com.raulastete.notemark.di.loginModule
import com.raulastete.notemark.di.registrationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NoteMarkApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@NoteMarkApp)
            modules(
                appModule,
                loginModule,
                registrationModule,
                homeModule,
                dataModule
            )
        }
    }
}