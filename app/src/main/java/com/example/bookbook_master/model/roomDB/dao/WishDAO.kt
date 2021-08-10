package com.example.bookbook_master.model.roomDB.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.example.bookbook_master.model.roomDB.entity.Wish

@Dao
interface WishDAO {
    // 중복 처리
    @Insert(onConflict = REPLACE)
    suspend fun addWish(wish: Wish)

    @Insert
    suspend fun addWishDb(wishs : List<Wish>)

    @Update
    suspend fun updateWish(wish: Wish)

    @Delete
    suspend fun deleteWish(wish: Wish)

    @Query("SELECT title, * FROM wish GROUP BY title")
    fun getAll() : LiveData<List<Wish>>
}