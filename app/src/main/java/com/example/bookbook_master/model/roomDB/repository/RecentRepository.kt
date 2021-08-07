package com.example.bookbook_master.model.roomDB.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.bookbook_master.model.roomDB.AppDatabase
import com.example.bookbook_master.model.roomDB.dao.RecentDAO
import com.example.bookbook_master.model.roomDB.entity.Recent
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class RecentRepository(private val recentDAO: RecentDAO) {

    val readAllData : Flow<List<Recent>> = recentDAO.readAllData()

    suspend fun addRecent(recent: Recent){
        recentDAO.addRecent(recent)
    }

    suspend fun updateRecent(recent : Recent){
        recentDAO.updateRecent(recent)
    }

    suspend fun deleteRecent(recent : Recent){
        recentDAO.deleteRecent(recent)
    }
}