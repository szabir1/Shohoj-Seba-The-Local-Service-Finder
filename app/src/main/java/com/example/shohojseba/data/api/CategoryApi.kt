package com.example.shohojseba.data.api


import com.example.shohojseba.data.model.Category
import retrofit2.http.GET
import retrofit2.http.Headers



interface CategoryApi {


    @Headers(
        "Accept: application/json"
    )
    @GET("category")
    suspend fun getCategories(): List<Category>


}