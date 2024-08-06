package com.nafis.skilltestprodia.response

import com.google.gson.annotations.SerializedName

data class CategoryResponse(

	@field:SerializedName("news_sites")
	val newsSites: List<String>,

	@field:SerializedName("version")
	val version: String
)
