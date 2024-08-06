package com.nafis.skilltestprodia.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nafis.skilltestprodia.api.ApiConfig
import com.nafis.skilltestprodia.response.CategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryViewModel: ViewModel() {

    private val _categoryData = MutableLiveData<List<String>>()
    val categoryData: LiveData<List<String>> = _categoryData

    init {
        getCategory()
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

    companion object {
        private const val TAG = "MainActivity"
    }
}