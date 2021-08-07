package com.example.bookbook_master.model.roomDB.entity

import androidx.room.*
import com.example.bookbook_master.model.data.Document

@Entity(tableName = "recent")
data class Recent(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @Embedded(prefix = "recent_") var document: Document
)