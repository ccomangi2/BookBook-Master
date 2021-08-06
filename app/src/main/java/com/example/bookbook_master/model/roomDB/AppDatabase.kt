package com.example.bookbook_master.model.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookbook_master.model.roomDB.dao.RecentDAO
import com.example.bookbook_master.model.roomDB.entity.Recent

@Database(entities = [Recent::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recentDao(): RecentDAO

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "database-recent"
                    ).build()
                }
            }
            return instance
        }
    }
}