package com.nafis.skilltestprodia.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.nafis.skilltestprodia.database.SearchHistory
import com.nafis.skilltestprodia.database.SearchHistoryDao
import com.nafis.skilltestprodia.database.SearchHistoryRoom
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SearchHistoryRepository(application: Application) {
    private val historyDao: SearchHistoryDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = SearchHistoryRoom.getDatabase(application)
        historyDao = db.historyDao()
    }

    fun getAllHistory(): LiveData<List<SearchHistory>> = historyDao.getAllHistory()

    fun insert(searchHistory: SearchHistory) {
        executorService.execute { historyDao.insert(searchHistory) }
    }

    fun delete(searchHistory: SearchHistory) {
        executorService.execute { historyDao.delete(searchHistory) }
    }
}