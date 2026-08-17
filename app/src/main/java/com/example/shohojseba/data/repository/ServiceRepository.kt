package com.example.shohojseba.data.repository


import android.util.Log
import com.example.shohojseba.data.api.RetrofitClient
import com.example.shohojseba.data.model.ServiceDetails



class ServiceRepository {


    private val api =
        RetrofitClient.serviceApi



    suspend fun getServicesByCategory(
        categoryId: Long
    ): List<ServiceDetails> {


        return try {


            Log.d(
                "SERVICE_TEST",
                "Loading services for category = $categoryId"
            )


            val result =
                api.getServicesByCategory(
                    "eq.$categoryId"
                )


            Log.d(
                "SERVICE_TEST",
                "CATEGORY SIZE = ${result.size}"
            )


            Log.d(
                "SERVICE_TEST",
                "CATEGORY DATA = $result"
            )


            result


        } catch (e: Exception) {


            Log.e(
                "SERVICE_TEST",
                "CATEGORY ERROR = ${e.message}"
            )


            emptyList()

        }


    }


}