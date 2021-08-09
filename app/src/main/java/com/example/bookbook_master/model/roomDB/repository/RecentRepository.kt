package com.example.bookbook_master.model.roomDB.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.roomDB.dao.RecentDAO
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow

class RecentRepository(private val recentDAO: RecentDAO?) {

    val readAllData : Flow<List<Recent>>? = recentDAO?.readAllData()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addRecent(recent: Recent){
        recentDAO?.addRecent(recent)
    }

    suspend fun getBookList(recent: Recent) = recentDAO?.readAllData()
}