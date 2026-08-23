package com.example.shohojseba.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shohojseba.data.UserSession
import com.example.shohojseba.data.model.Booking
import com.example.shohojseba.data.model.BookingRequest
import com.example.shohojseba.data.repository.BookingRepository
import kotlinx.coroutines.launch

class BookingViewModel : ViewModel() {

    private val repository = BookingRepository()

    // Loading

    private val _isLoading =
        mutableStateOf(false)

    val isLoading: State<Boolean> =
        _isLoading

    // Success

    private val _bookingSuccess =
        mutableStateOf(false)

    val bookingSuccess: State<Boolean> =
        _bookingSuccess

    // Booking list

    private val _bookings =
        mutableStateOf<List<Booking>>(emptyList())

    val bookings: State<List<Booking>> =
        _bookings

    // =====================================================
    // Customer creates booking
    // =====================================================

    fun createBooking(
        booking: BookingRequest
    ) {

        viewModelScope.launch {

            _isLoading.value = true

            val result =
                repository.createBooking(booking)

            _bookingSuccess.value =
                result != null

            _isLoading.value = false

        }

    }

    // =====================================================
    // Customer booking history
    // =====================================================

    fun loadCustomerBookings() {

        val customerId =
            UserSession.customerId ?: return

        viewModelScope.launch {

            _isLoading.value = true

            _bookings.value =
                repository.getBookingsByCustomer(customerId)

            Log.d(
                "BOOKING_TEST",
                "Customer bookings = ${_bookings.value}"
            )

            _isLoading.value = false

        }

    }

    // =====================================================
    // Provider booking requests
    // =====================================================

    fun loadProviderBookings() {

        viewModelScope.launch {

            _isLoading.value = true

            val providerId =
                UserSession.providerId

            Log.d(
                "BOOKING_TEST",
                "Provider Session ID = $providerId"
            )

            if (providerId == null) {

                Log.e(
                    "BOOKING_TEST",
                    "Provider ID is NULL"
                )

                _bookings.value = emptyList()

                _isLoading.value = false

                return@launch

            }

            val bookings =
                repository.getBookingsByProvider(providerId)

            Log.d(
                "BOOKING_TEST",
                "Provider bookings = $bookings"
            )

            _bookings.value = bookings

            _isLoading.value = false

        }

    }

    // =====================================================
    // Update booking status
    // =====================================================

    fun updateStatus(
        bookingId: Long,
        status: String
    ) {

        viewModelScope.launch {

            _isLoading.value = true

            val success =
                repository.updateBookingStatus(
                    bookingId,
                    status
                )

            if (success) {

                loadProviderBookings()

            }

            _isLoading.value = false

        }

    }

    fun acceptBooking(
        bookingId: Long
    ) {

        updateStatus(
            bookingId,
            "Accepted"
        )

    }

    fun rejectBooking(
        bookingId: Long
    ) {

        updateStatus(
            bookingId,
            "Rejected"
        )

    }

    // =====================================================
    // Reset booking dialog
    // =====================================================

    fun resetBookingState() {

        _bookingSuccess.value = false

    }

}