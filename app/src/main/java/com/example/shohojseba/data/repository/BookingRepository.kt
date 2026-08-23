package com.example.shohojseba.data.repository

import android.util.Log

import com.example.shohojseba.data.api.RetrofitClient
import com.example.shohojseba.data.model.Booking
import com.example.shohojseba.data.model.BookingRequest

import retrofit2.HttpException


class BookingRepository {


    private val api =
        RetrofitClient.bookingApi


    // =====================================================
    // CREATE BOOKING
    // =====================================================

    suspend fun createBooking(

        booking: BookingRequest

    ): Booking? {

        return try {


            Log.d(
                "BOOKING_TEST",
                "Creating booking: $booking"
            )


            api
                .createBooking(
                    booking
                )
                .firstOrNull()


        } catch (e: HttpException) {


            Log.e(
                "BOOKING_TEST",
                "HTTP ${e.code()} -> ${
                    e.response()
                        ?.errorBody()
                        ?.string()
                }"
            )


            null


        } catch (e: Exception) {


            Log.e(
                "BOOKING_TEST",
                e.message ?: ""
            )


            null

        }

    }


    // =====================================================
    // PROVIDER BOOKINGS
    // =====================================================

    suspend fun getBookingsByProvider(

        providerId: Long

    ): List<Booking> {

        return try {


            api.getBookingsByProvider(

                "eq.$providerId"

            )


        } catch (e: Exception) {


            Log.e(
                "BOOKING_TEST",
                e.message ?: ""
            )


            emptyList()

        }

    }


    // =====================================================
    // CUSTOMER BOOKINGS
    // =====================================================

    suspend fun getBookingsByCustomer(

        customerId: Long

    ): List<Booking> {

        return try {


            api.getBookingsByCustomer(

                "eq.$customerId"

            )


        } catch (e: Exception) {


            Log.e(
                "BOOKING_TEST",
                e.message ?: ""
            )


            emptyList()

        }

    }


    // =====================================================
    // UPDATE BOOKING STATUS
    // =====================================================

    suspend fun updateBookingStatus(

        bookingId: Long,

        status: String

    ): Boolean {

        return try {


            api.updateBookingStatus(

                bookingId =
                    "eq.$bookingId",

                body =
                    mapOf(
                        "status" to status
                    )

            )


            true


        } catch (e: Exception) {


            Log.e(
                "BOOKING_TEST",
                "STATUS UPDATE ERROR = ${e.message}"
            )


            false

        }

    }

}