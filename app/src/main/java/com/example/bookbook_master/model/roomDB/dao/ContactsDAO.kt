package com.example.bookbook_master.model.roomDB.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bookbook_master.model.roomDB.entity.Contacts

/*
 * 엔티티를 쿼리로 접근하는 인터페이스
 */
@Dao
interface ContactsDao {
    // 데이터를 전부 가져올 함수
    @Query("SELECT * FROM tb_contacts")
    fun getAll(): LiveData<List<Contacts>>

    // 데이터를 넣을 함수
    @Insert
    fun insertAll(vararg contacts: Contacts)

    // 업데이트 함수
    @Update
    fun update(contacts: Contacts)

    // 데이터를 삭제할 함수
    @Delete
    fun delete(contacts: Contacts)
}