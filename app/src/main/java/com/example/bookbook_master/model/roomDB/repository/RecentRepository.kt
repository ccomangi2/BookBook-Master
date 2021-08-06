package com.example.bookbook_master.model.roomDB.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.bookbook_master.model.roomDB.AppDatabase
import com.example.bookbook_master.model.roomDB.dao.RecentDAO
import com.example.bookbook_master.model.roomDB.entity.Recent

class RecentRepository(application: Application) {
    private val recentDao: RecentDAO
    private val recentList: LiveData<List<Recent>>

    init {
        var db = AppDatabase.getInstance(application)
        recentDao = db!!.recentDao()
        recentList = db.recentDao().getAll()
    }

    fun insert(recent: Recent) {
        recentDao.insert(recent)
    }

    fun delete(recent: Recent){
        recentDao.delete(recent)
    }

    fun getAll(): LiveData<List<Recent>> {
        return recentDao.getAll()
    }
}