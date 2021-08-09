package com.example.bookbook_master.model.roomDB.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bookbook_master.model.roomDB.entity.Recent

@Dao
interface RecentDAO {
    @Insert
    suspend fun addRecent(recent: Recent)

    @Insert
    suspend fun addRecentDb(recents : List<Recent>)

    @Update
    suspend fun updateRecent(recent : Recent)

    @Delete
    suspend fun deleteRecent(recent : Recent)

    @Query("SELECT * FROM recent ORDER BY id ASC")
    fun getAll() : LiveData<List<Recent>>
}