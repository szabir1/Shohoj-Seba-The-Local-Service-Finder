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
    // CREATE BOOKING / QUOTATION REQUEST
    // ALSO CREATE PROVIDER NOTIFICATION
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


            if (
                result != null
            ) {

                // =================================================
                // PROVIDER NOTIFICATION
                // =================================================

                val providerNotificationResult =
                    notificationRepository
                        .createProviderNotification(

                            providerId =
                                booking.provider_id,

                            bookingId =
                                result.bookingId,

                            title =

                                if (
                                    booking.quotation_requested
                                ) {

                                    "New Quotation Request"

                                } else {

                                    "New Booking Request"

                                },

                            message =

                                if (
                                    booking.quotation_requested
                                ) {

                                    "A customer requested a custom quotation for your service."

                                } else {

                                    "You received a new service booking request."

                                },

                            type =

                                if (
                                    booking.quotation_requested
                                ) {

                                    "QUOTATION_REQUEST"

                                } else {

                                    "NEW_BOOKING"

                                }

                        )


                Log.d(

                    "PROVIDER_NOTIFICATION_TEST",

                    "NEW REQUEST notification result = ${
                        providerNotificationResult.isSuccess
                    }"

                )

            }


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


            Log.d(

                "BOOKING_TEST",

                "Customer bookings = ${_bookings.value}"

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


            Log.d(

                "BOOKING_TEST",

                "Provider Session ID = $providerId"

            )


            if (
                providerId == null
            ) {

                Log.e(

                    "BOOKING_TEST",

                    "Provider ID is NULL"

                )


                _bookings.value =
                    emptyList()


                _isLoading.value =
                    false


                return@launch

            }


            val bookings =
                repository
                    .getBookingsByProvider(
                        providerId
                    )


            Log.d(

                "BOOKING_TEST",

                "Provider bookings = $bookings"

            )


            _bookings.value =
                bookings


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // ACCEPT NORMAL BOOKING
    // CUSTOMER NOTIFICATION
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
                        ?: "the provider"


                val notificationResult =
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


                Log.d(

                    "NOTIFICATION_TEST",

                    "ACCEPT notification result = ${notificationResult.isSuccess}"

                )


                loadProviderBookings()

            }


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // REJECT BOOKING / QUOTATION REQUEST
    // CUSTOMER NOTIFICATION
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


                val notificationTitle =

                    if (
                        booking.quotationRequested
                    ) {

                        "Quotation Request Rejected"

                    } else {

                        "Booking Rejected"

                    }


                val notificationMessage =

                    if (
                        booking.quotationRequested
                    ) {

                        "Your quotation request for $serviceName was rejected."

                    } else {

                        "Your $serviceName booking request was rejected."

                    }


                val notificationResult =
                    notificationRepository
                        .createNotification(

                            customerId =
                                booking.customerId,

                            bookingId =
                                booking.bookingId,

                            title =
                                notificationTitle,

                            message =
                                notificationMessage,

                            type =
                                "REJECTED"

                        )


                Log.d(

                    "NOTIFICATION_TEST",

                    "REJECT notification result = ${notificationResult.isSuccess}"

                )


                loadProviderBookings()

            }


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // PROVIDER SEND QUOTATION
    // CUSTOMER NOTIFICATION
    // =====================================================

    fun sendQuotation(

        booking: Booking,

        quotedPrice: Double,

        message: String

    ) {

        viewModelScope.launch {

            _isLoading.value =
                true


            val success =
                repository
                    .sendQuotation(

                        bookingId =
                            booking.bookingId,

                        quotedPrice =
                            quotedPrice,

                        message =
                            message

                    )


            if (
                success
            ) {

                val serviceName =
                    booking.service
                        ?.serviceName
                        ?: "service"


                val notificationResult =
                    notificationRepository
                        .createNotification(

                            customerId =
                                booking.customerId,

                            bookingId =
                                booking.bookingId,

                            title =
                                "Quotation Received",

                            message =
                                "You received a quotation of ৳$quotedPrice for $serviceName.",

                            type =
                                "QUOTATION"

                        )


                Log.d(

                    "NOTIFICATION_TEST",

                    "QUOTATION notification result = ${notificationResult.isSuccess}"

                )


                loadProviderBookings()

            }


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // CUSTOMER ACCEPT QUOTATION
    // PROVIDER NOTIFICATION
    // =====================================================

    fun acceptQuotation(

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


                val providerNotificationResult =
                    notificationRepository
                        .createProviderNotification(

                            providerId =
                                booking.providerId,

                            bookingId =
                                booking.bookingId,

                            title =
                                "Quotation Accepted",

                            message =
                                "The customer accepted your quotation of ৳${
                                    booking.quotedPrice ?: 0.0
                                } for $serviceName.",

                            type =
                                "QUOTATION_ACCEPTED"

                        )


                Log.d(

                    "PROVIDER_NOTIFICATION_TEST",

                    "QUOTATION ACCEPT notification result = ${
                        providerNotificationResult.isSuccess
                    }"

                )


                loadCustomerBookings()

            }


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // CUSTOMER REJECT QUOTATION
    // PROVIDER NOTIFICATION
    // =====================================================

    fun rejectQuotation(

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


                val providerNotificationResult =
                    notificationRepository
                        .createProviderNotification(

                            providerId =
                                booking.providerId,

                            bookingId =
                                booking.bookingId,

                            title =
                                "Quotation Rejected",

                            message =
                                "The customer rejected your quotation for $serviceName.",

                            type =
                                "QUOTATION_REJECTED"

                        )


                Log.d(

                    "PROVIDER_NOTIFICATION_TEST",

                    "QUOTATION REJECT notification result = ${
                        providerNotificationResult.isSuccess
                    }"

                )


                loadCustomerBookings()

            }


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // COMPLETE BOOKING
    // CUSTOMER NOTIFICATION + EXISTING REMINDER
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

                val serviceName =
                    booking.service
                        ?.serviceName
                        ?: "service"


                // =================================================
                // CUSTOMER COMPLETION NOTIFICATION
                // =================================================

                val notificationResult =
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


                Log.d(

                    "NOTIFICATION_TEST",

                    "COMPLETE notification result = ${notificationResult.isSuccess}"

                )


                // =================================================
                // EXISTING SERVICE REMINDER
                // =================================================

                val reminderResult =
                    reminderRepository
                        .createReminderForCompletedBooking(
                            booking
                        )


                if (
                    reminderResult.isSuccess
                ) {

                    Log.d(

                        "REMINDER_TEST",

                        "Reminder result = ${
                            reminderResult.getOrNull()
                        }"

                    )

                } else {

                    Log.e(

                        "REMINDER_TEST",

                        "Reminder failed = ${
                            reminderResult
                                .exceptionOrNull()
                                ?.message
                        }"

                    )

                }


                loadProviderBookings()

            }


            _isLoading.value =
                false

        }

    }


    // =====================================================
    // RESET BOOKING STATE
    // =====================================================

    fun resetBookingState() {

        _bookingSuccess.value =
            false

    }

}