package com.example.shohojseba.data.repository


import android.util.Log
import com.example.shohojseba.data.api.RetrofitClient
import com.example.shohojseba.data.model.Category


class CategoryRepository {


    private val api = RetrofitClient.categoryApi



    suspend fun getCategories(): List<Category> {


        return try {


            Log.d(
                "CATEGORY_TEST",
                "Starting Retrofit category query"
            )


            val result = api.getCategories()


            Log.d(
                "CATEGORY_TEST",
                "SIZE = ${result.size}"
            )


            Log.d(
                "CATEGORY_TEST",
                "DATA = $result"
            )


            result



        } catch (e: Exception) {


            Log.e(
                "CATEGORY_TEST",
                "ERROR = ${e.message}"
            )


            emptyList()

        }


    }


}