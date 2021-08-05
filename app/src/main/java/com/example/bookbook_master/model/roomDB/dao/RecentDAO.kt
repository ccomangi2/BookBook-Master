package com.example.bookbook_master.model.roomDB.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.bookbook_master.model.roomDB.entity.Recent

interface RecentDAO {
    @Query("SELECT * FROM recent")
    fun getAll(): List<Recent>

    @Insert
    fun insertAll(vararg recent: Recent)

    @Delete
    fun delete(recent: Recent)
}