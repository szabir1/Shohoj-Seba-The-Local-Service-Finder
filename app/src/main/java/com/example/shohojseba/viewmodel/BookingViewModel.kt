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
import com.example.shohojseba.data.repository.NotificationRepository
import com.example.shohojseba.data.repository.ServiceReminderRepository

import kotlinx.coroutines.launch


class BookingViewModel : ViewModel() {


    private val repository =
        BookingRepository()


    private val reminderRepository =
        ServiceReminderRepository()


    private val notificationRepository =
        NotificationRepository()


    // =====================================================
    // LOADING
    // =====================================================

    private val _isLoading =
        mutableStateOf(false)

    val isLoading:
            State<Boolean> =
        _isLoading


    // =====================================================
    // BOOKING SUCCESS
    // =====================================================

    private val _bookingSuccess =
        mutableStateOf(false)

    val bookingSuccess:
            State<Boolean> =
        _bookingSuccess


    // =====================================================
    // BOOKINGS
    // =====================================================

    private val _bookings =
        mutableStateOf<List<Booking>>(
            emptyList()
        )

    val bookings:
            State<List<Booking>> =
        _bookings


    // =====================================================
    // CREATE BOOKING
    // =====================================================

    fun createBooking(

        booking: BookingRequest

    ) {


        viewModelScope.launch {


            _isLoading.value =
                true


            val result =
                repository
                    .createBooking(
                        booking
                    )


            _bookingSuccess.value =
                result != null


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // CUSTOMER BOOKINGS
    // =====================================================

    fun loadCustomerBookings() {


        val customerId =
            UserSession.customerId
                ?: return


        viewModelScope.launch {


            _isLoading.value =
                true


            _bookings.value =
                repository
                    .getBookingsByCustomer(
                        customerId
                    )


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // PROVIDER BOOKINGS
    // =====================================================

    fun loadProviderBookings() {


        viewModelScope.launch {


            _isLoading.value =
                true


            val providerId =
                UserSession.providerId


            if (
                providerId == null
            ) {


                _bookings.value =
                    emptyList()


                _isLoading.value =
                    false


                return@launch

            }


            _bookings.value =
                repository
                    .getBookingsByProvider(
                        providerId
                    )


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // ACCEPT BOOKING
    // =====================================================

    fun acceptBooking(

        booking: Booking

    ) {


        viewModelScope.launch {


            _isLoading.value =
                true


            val success =
                repository
                    .updateBookingStatus(

                        bookingId =
                            booking.bookingId,

                        status =
                            "Accepted"

                    )


            if (
                success
            ) {


                val serviceName =

                    booking.service
                        ?.serviceName
                        ?: "service"


                val providerName =

                    booking.provider
                        ?.name
                        ?: "your provider"


                notificationRepository
                    .createNotification(

                        customerId =
                            booking.customerId,

                        bookingId =
                            booking.bookingId,

                        title =
                            "Booking Accepted",

                        message =
                            "Your $serviceName booking has been accepted by $providerName.",

                        type =
                            "ACCEPTED"

                    )


                loadProviderBookings()

            }


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // REJECT BOOKING
    // =====================================================

    fun rejectBooking(

        booking: Booking

    ) {


        viewModelScope.launch {


            _isLoading.value =
                true


            val success =
                repository
                    .updateBookingStatus(

                        bookingId =
                            booking.bookingId,

                        status =
                            "Rejected"

                    )


            if (
                success
            ) {


                val serviceName =

                    booking.service
                        ?.serviceName
                        ?: "service"


                notificationRepository
                    .createNotification(

                        customerId =
                            booking.customerId,

                        bookingId =
                            booking.bookingId,

                        title =
                            "Booking Rejected",

                        message =
                            "Your $serviceName booking request was rejected.",

                        type =
                            "REJECTED"

                    )


                loadProviderBookings()

            }


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // COMPLETE BOOKING
    // =====================================================

    fun completeBooking(

        booking: Booking

    ) {


        viewModelScope.launch {


            _isLoading.value =
                true


            val completed =
                repository
                    .updateBookingStatus(

                        bookingId =
                            booking.bookingId,

                        status =
                            "Completed"

                    )


            if (
                completed
            ) {


                // =============================================
                // COMPLETION NOTIFICATION
                // =============================================

                val serviceName =

                    booking.service
                        ?.serviceName
                        ?: "service"


                notificationRepository
                    .createNotification(

                        customerId =
                            booking.customerId,

                        bookingId =
                            booking.bookingId,

                        title =
                            "Service Completed",

                        message =
                            "Your $serviceName has been marked as completed.",

                        type =
                            "COMPLETED"

                    )


                // =============================================
                // AC SERVICE REMINDER
                // =============================================

                val reminderResult =
                    reminderRepository
                        .createReminderForCompletedBooking(
                            booking
                        )


                Log.d(
                    "REMINDER_TEST",
                    "Reminder result = ${reminderResult.getOrNull()}"
                )


                loadProviderBookings()

            }


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // RESET
    // =====================================================

    fun resetBookingState() {

        _bookingSuccess.value =
            false

    }

}