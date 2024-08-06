package com.nafis.skilltestprodia.api

import com.nafis.skilltestprodia.response.ArticleResponse
import com.nafis.skilltestprodia.response.CategoryResponse
import com.nafis.skilltestprodia.response.ResultsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("articles/")
    fun getArticles(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<ArticleResponse>

    @GET("articles/{id}/")
    fun getDetailArticles(
        @Path("id") id: Int?
    ): Call<ResultsItem>

    @GET("info/")
    fun getCategory(
    ): Call<CategoryResponse>
}
