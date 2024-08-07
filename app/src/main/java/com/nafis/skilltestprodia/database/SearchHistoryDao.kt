package com.nafis.skilltestprodia.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(history: SearchHistory)

    @Delete
    fun delete(history: SearchHistory)

    @Query("SELECT * from search_history ORDER BY id ASC")
    fun getAllHistory(): LiveData<List<SearchHistory>>
}