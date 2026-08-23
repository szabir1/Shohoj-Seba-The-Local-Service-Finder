package com.example.shohojseba.data.api

import com.example.shohojseba.data.model.Review
import com.example.shohojseba.data.model.ReviewRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ReviewApi {

    @Headers("Prefer: return=representation")
    @POST("review")
    suspend fun createReview(
        @Body review: ReviewRequest
    ): List<Review>


    @GET("review")
    suspend fun getReviewsByProvider(
        @Query("provider_id") providerId: String
    ): List<Review>


    @GET("review")
    suspend fun getReviewByBooking(
        @Query("booking_id") bookingId: String
    ): List<Review>
}