package com.example.bookbook_master.model.roomDB.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bookbook_master.model.roomDB.entity.Recent

@Dao
interface RecentDAO {
    @Query("SELECT * FROM recent")
    fun getAll(): LiveData<List<Recent>>

    @Insert
    fun insertAll(vararg recent: Recent)

    @Insert
    fun insert(recent: Recent)

    @Update
    fun update(recent: Recent)

    @Delete
    fun delete(recent: Recent)
}