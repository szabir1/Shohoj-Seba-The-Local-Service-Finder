package com.example.shohojseba.data.api


import com.example.shohojseba.data.model.ServiceDetails
import retrofit2.http.GET
import retrofit2.http.Query



interface ServiceApi {


    @GET("service_details")
    suspend fun getServicesByCategory(

        @Query("category_id")
        categoryFilter: String

    ): List<ServiceDetails>


}