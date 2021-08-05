package com.example.bookbook_master.model.roomDB.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bookbook_master.model.data.Document

@Entity(tableName = "recent")
data class Recent(
    @PrimaryKey(autoGenerate = true)
    var document: Document
)