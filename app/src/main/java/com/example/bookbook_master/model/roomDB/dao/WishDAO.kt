package com.example.bookbook_master.model.roomDB.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.example.bookbook_master.model.roomDB.entity.Wish
import retrofit2.Converter

@Dao
interface WishDAO {
    // 중복 처리
    @Insert(onConflict = REPLACE)
    suspend fun addWish(wish: Wish)

    @Insert
    suspend fun addWishDb(wishs : List<Wish>)

    @Update
    suspend fun updateWish(wish: Wish)

    // 하나만 삭제
    @Query("DELETE FROM wish WHERE title = :title")
    fun deleteWish(title: String)

    //출간일(기본)
    @Query("SELECT title, * FROM wish GROUP BY title ORDER BY datetime DESC")
    fun getAll() : LiveData<List<Wish>>

    //최고가
    @Query("SELECT title, * FROM wish GROUP BY title ORDER BY price ASC")
    fun getLowAll() : LiveData<List<Wish>>

    //최저가
    @Query("SELECT title, * FROM wish GROUP BY title ORDER BY price DESC")
    fun getHighAll() : LiveData<List<Wish>>

    // 전부 삭제
    @Query("DELETE FROM wish")
    fun deleteAll()
}