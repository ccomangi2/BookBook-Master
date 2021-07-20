package com.example.bookbook_master

import android.app.Application
import com.example.bookbook_master.model.modul.apiModule
import com.example.bookbook_master.model.modul.repositoryModule
import com.example.bookbook_master.model.modul.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BookBookApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BookBookApplication)
            modules(mutableListOf(viewModelModule, repositoryModule, apiModule))
        }
    }
}