package com.nafis.skilltestprodia.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nafis.skilltestprodia.database.SearchHistory
import com.nafis.skilltestprodia.repository.SearchHistoryRepository

class SearchHistoryViewModel(application: Application) : ViewModel() {

    private val historyRepository: SearchHistoryRepository = SearchHistoryRepository(application)

    fun getAllHistory(): LiveData<List<SearchHistory>> = historyRepository.getAllHistory()

    fun insert(searchHistory: SearchHistory) {
        historyRepository.insert(searchHistory)
    }

    fun delete(searchHistory: SearchHistory) {
        historyRepository.delete(searchHistory)
    }
}