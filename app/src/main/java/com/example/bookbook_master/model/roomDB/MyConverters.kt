package com.example.bookbook_master.model.roomDB

import androidx.room.TypeConverter
import com.example.bookbook_master.model.roomDB.entity.Recent
import com.google.gson.Gson

class MyConverters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun toStringList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun fromFloatList(value: List<Float>?): String = Gson().toJson(value)

    @TypeConverter
    fun toFloatList(value: String) = Gson().fromJson(value, Array<Float>::class.java).toList()
}