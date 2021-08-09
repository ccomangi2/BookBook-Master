package com.example.bookbook_master

import android.app.Application
import com.example.bookbook_master.model.modul.apiModule
import com.example.bookbook_master.model.modul.repositoryModule
import com.example.bookbook_master.model.modul.viewModelModule
import com.example.bookbook_master.model.roomDB.AppDatabase
import com.example.bookbook_master.model.roomDB.repository.RecentRepository
import com.example.bookbook_master.view.fragment.MainFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
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