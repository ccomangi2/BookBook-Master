package com.example.bookbook_master.model.roomDB.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.bookbook_master.model.roomDB.entity.Recent

@Dao
interface RecentDAO {
    // 중복 처리
    @Insert(onConflict = REPLACE)
    suspend fun addRecent(recent: Recent)

    @Insert
    suspend fun addRecentDb(recents : List<Recent>)

    @Update
    suspend fun updateRecent(recent : Recent)

    @Delete
    suspend fun deleteRecent(recent : Recent)

    @Query("SELECT title, * FROM recent GROUP BY title ORDER BY id DESC LIMIT 30")
    fun getAll() : LiveData<List<Recent>>
}