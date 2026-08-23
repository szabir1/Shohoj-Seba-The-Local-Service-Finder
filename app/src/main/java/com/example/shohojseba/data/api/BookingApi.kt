package com.example.shohojseba.data.api

import com.example.shohojseba.data.model.Booking
import com.example.shohojseba.data.model.BookingRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface BookingApi {

    @Headers("Prefer: return=representation")
    @POST("booking")
    suspend fun createBooking(
        @Body booking: BookingRequest
    ): List<Booking>


    // =====================================================
    // PROVIDER BOOKING REQUESTS
    // =====================================================

    @GET("booking")
    suspend fun getBookingsByProvider(

        @Query("provider_id")
        providerId: String,

        @Query("select")
        select: String =
            "*,customer:Customer!booking_customer_id_fkey(customer_id,name,phone),provider:Provider!booking_provider_id_fkey(provider_id,name,phone),service:Service!booking_service_id_fkey(service_id,service_name)"

    ): List<Booking>


    // =====================================================
    // CUSTOMER BOOKING HISTORY
    // =====================================================

    @GET("booking")
    suspend fun getBookingsByCustomer(

        @Query("customer_id")
        customerId: String,

        @Query("select")
        select: String =
            "*,customer:Customer!booking_customer_id_fkey(customer_id,name,phone),provider:Provider!booking_provider_id_fkey(provider_id,name,phone),service:Service!booking_service_id_fkey(service_id,service_name)"

    ): List<Booking>


    // =====================================================
    // UPDATE STATUS
    // =====================================================

    @Headers("Prefer: return=representation")
    @PATCH("booking")
    suspend fun updateBookingStatus(

        @Query("booking_id")
        bookingId: String,

        @Body
        body: Map<String, String>

    ): List<Booking>
}