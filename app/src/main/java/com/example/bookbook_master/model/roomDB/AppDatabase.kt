package com.example.bookbook_master.model.roomDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.bookbook_master.model.data.Document
import com.example.bookbook_master.model.roomDB.dao.RecentDAO
import com.example.bookbook_master.model.roomDB.entity.Recent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Recent::class], version = 1, exportSchema = false)
@TypeConverters(MyConverters::class)
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
                    )
//                        .addCallback(object : RoomDatabase.Callback(){
//                        override fun onCreate(db: SupportSQLiteDatabase) {
//                            super.onCreate(db)
//                            fillInDb(context.applicationContext)
//                        }
//                    })
                        .build()
                }
            }
            return instance
        }
//        // 데이터 미리 채우기
//        fun fillInDb(context: Context){
//            CoroutineScope(Dispatchers.IO).launch {
//                getInstance(context)!!.recentDao().addRecentDb(
//                    USER_DATA
//                )
//            }
//        }
    }
}
lateinit var document: Document
private val USER_DATA = arrayListOf(
    Recent(0,document),
    Recent(0,document)
)