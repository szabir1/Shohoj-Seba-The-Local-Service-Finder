package com.example.shohojseba.data.api


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {


    private const val BASE_URL =
        "https://spmffufgmzeyvndqknom.supabase.co/rest/v1/"


    private const val SUPABASE_ANON_KEY =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNwbWZmdWZnbXpleXZuZHFrbm9tIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODY5MzU5MDQsImV4cCI6MjEwMjUxMTkwNH0.GHN_dkhyXVmQfaFUyUGnrC2SqjRzNAp6NZKZedylU3w"



    private val loggingInterceptor =
        HttpLoggingInterceptor().apply {

            level = HttpLoggingInterceptor.Level.BODY

        }



    private val client =
        OkHttpClient.Builder()

            .addInterceptor { chain ->


                val request =
                    chain.request()
                        .newBuilder()

                        .addHeader(
                            "apikey",
                            SUPABASE_ANON_KEY
                        )

                        .addHeader(
                            "Authorization",
                            "Bearer $SUPABASE_ANON_KEY"
                        )

                        .addHeader(
                            "Accept",
                            "application/json"
                        )

                        .build()


                chain.proceed(request)

            }

            .addInterceptor(loggingInterceptor)

            .build()



    private val retrofit =
        Retrofit.Builder()

            .baseUrl(BASE_URL)

            .client(client)

            .addConverterFactory(
                GsonConverterFactory.create()
            )

            .build()



    // Category API
    val categoryApi: CategoryApi =
        retrofit.create(CategoryApi::class.java)



    // Service API
    val serviceApi: ServiceApi =
        retrofit.create(ServiceApi::class.java)



}