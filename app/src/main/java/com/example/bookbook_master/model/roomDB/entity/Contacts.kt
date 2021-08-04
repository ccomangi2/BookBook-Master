package com.example.bookbook_master.model.roomDB.entity

import androidx.room.*
import com.example.bookbook_master.model.data.Document

@Entity(tableName = "tb_contacts")
data class Contacts(
    @PrimaryKey(autoGenerate = true)
    var recent: Boolean = false, // 최근 본 상품
    var wish: Boolean = false // 위시 상품
)