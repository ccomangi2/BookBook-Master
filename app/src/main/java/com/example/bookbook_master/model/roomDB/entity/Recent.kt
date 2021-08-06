package com.example.bookbook_master.model.roomDB.entity

import androidx.room.*
import com.example.bookbook_master.model.data.Document
import org.jetbrains.annotations.Nullable

@Entity(tableName = "recent")
data class Recent(
    @PrimaryKey(autoGenerate = true) var index: Int,
    @Embedded(prefix = "recent_") var document: Document
)