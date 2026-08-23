package com.example.shohojseba.data.model

import com.google.gson.annotations.SerializedName

data class Booking(

    @SerializedName("booking_id")
    val bookingId: Long = 0,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("booking_date")
    val bookingDate: String = "",

    @SerializedName("booking_time")
    val bookingTime: String = "",

    @SerializedName("address")
    val address: String = "",

    @SerializedName("problem_description")
    val problemDescription: String = "",

    @SerializedName("status")
    val status: String = "Pending",

    @SerializedName("customer_id")
    val customerId: Long = 0,

    @SerializedName("provider_id")
    val providerId: Long = 0,

    @SerializedName("service_id")
    val serviceId: Long = 0,


    // Joined Customer

    @SerializedName("customer")
    val customer: BookingCustomer? = null,


    // Joined Provider

    @SerializedName("provider")
    val provider: BookingProvider? = null,


    // Joined Service

    @SerializedName("service")
    val service: BookingService? = null
)


data class BookingCustomer(

    @SerializedName("customer_id")
    val customerId: Long = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("phone")
    val phone: String = ""
)


data class BookingProvider(

    @SerializedName("provider_id")
    val providerId: Long = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("phone")
    val phone: String = ""
)


data class BookingService(

    @SerializedName("service_id")
    val serviceId: Long = 0,

    @SerializedName("service_name")
    val serviceName: String = ""
)