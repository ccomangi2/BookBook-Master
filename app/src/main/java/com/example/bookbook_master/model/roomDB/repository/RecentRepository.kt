package com.example.bookbook_master.model.roomDB.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.bookbook_master.model.roomDB.AppDatabase
import com.example.bookbook_master.model.roomDB.dao.RecentDAO
import com.example.bookbook_master.model.roomDB.entity.Recent

class RecentRepository(application: Application) {
    private val recentDAO: RecentDAO
    private val recentList: LiveData<List<Recent>>

    init {
        var db = AppDatabase.getInstance(application)
        recentDAO = db!!.recentDao()
        recentList = db.recentDao().getAll()
    }

    suspend fun addRecent(recent: Recent) {
        recentDAO.addRecent(recent)
    }

    fun getAll(): LiveData<List<Recent>> {
        return recentDAO.getAll()
    }
}