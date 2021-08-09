package com.example.bookbook_master.model.roomDB.entity

import androidx.room.*
import com.example.bookbook_master.model.data.Document

@Entity(tableName = "wish")
data class Wish(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @Embedded var document: Document
)