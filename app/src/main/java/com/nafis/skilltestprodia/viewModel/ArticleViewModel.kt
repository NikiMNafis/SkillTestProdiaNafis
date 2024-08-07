package com.nafis.skilltestprodia.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nafis.skilltestprodia.api.ApiConfig
import com.nafis.skilltestprodia.response.ArticleResponse
import com.nafis.skilltestprodia.response.CategoryResponse
import com.nafis.skilltestprodia.response.ResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel: ViewModel() {

    private val _articlesData = MutableLiveData<List<ResultsItem>>()
    val articlesData: LiveData<List<ResultsItem>> = _articlesData

    private val _detailArticleData = MutableLiveData<ResultsItem>()
    val detailArticleData: LiveData<ResultsItem> = _detailArticleData

    private val _categoryData = MutableLiveData<List<String>>()
    val categoryData: LiveData<List<String>> = _categoryData

    private val _articleFilteredData = MutableLiveData<List<ResultsItem>>()
    val articleFilteredData: LiveData<List<ResultsItem>> = _articleFilteredData

    private val _articleFindData = MutableLiveData<List<ResultsItem>>()
    val articleFindData: LiveData<List<ResultsItem>> = _articleFindData

    init {
        getCategory()
    }

    fun getArticles() {
        val client = ApiConfig.getApiService().getArticles(30, 10)
        client.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _articlesData.value = response.body()?.results
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getDetailArticle(id: Int?) {
        val client = ApiConfig.getApiService().getDetailArticles(id)
        client.enqueue(object : Callback<ResultsItem> {
            override fun onResponse(call: Call<ResultsItem>, response: Response<ResultsItem>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailArticleData.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResultsItem>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun getCategory() {
        val client = ApiConfig.getApiService().getCategory()
        client.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _categoryData.value = response.body()?.newsSites
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun filterArticles(category: String) {
        val allArticles = _articlesData.value?: emptyList()
        val filteredArticles = if (category.isEmpty()) {
            allArticles
        } else {
            allArticles.filter { it.newsSite == category }
        }
        _articleFilteredData.value = filteredArticles!!
    }

    fun findArticle(query: String) {
        val allArticles = _articleFilteredData.value?: emptyList()
        val foundArticle = if (query.isEmpty()) {
            allArticles
        } else {
            allArticles.filter { it.title.contains(query ?: "", ignoreCase = true) }
        }
        _articleFindData.value = foundArticle!!
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}