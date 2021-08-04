package com.example.bookbook_master.model.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bookbook_master.model.roomDB.dao.ContactsDao
import com.example.bookbook_master.model.roomDB.entity.Contacts

@Database(entities = [Contacts::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database-contacts"
                )
                    .build()
            }
            return instance
        }
    }
}