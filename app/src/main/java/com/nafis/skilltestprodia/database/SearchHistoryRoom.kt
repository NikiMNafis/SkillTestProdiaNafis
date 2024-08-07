package com.nafis.skilltestprodia.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SearchHistory::class], version = 1, exportSchema = false)
abstract class SearchHistoryRoom : RoomDatabase() {

    abstract fun historyDao(): SearchHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: SearchHistoryRoom? = null

        @JvmStatic
        fun getDatabase(context: Context): SearchHistoryRoom {
            if (INSTANCE == null) {
                synchronized(SearchHistoryRoom::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        SearchHistoryRoom::class.java, "db_search_history"
                    )
                        .build()
                }
            }
            return INSTANCE as SearchHistoryRoom
        }
    }
}