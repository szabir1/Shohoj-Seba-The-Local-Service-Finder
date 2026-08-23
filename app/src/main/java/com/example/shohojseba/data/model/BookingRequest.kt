package com.example.shohojseba.data.model

data class BookingRequest(
    val booking_date: String,
    val booking_time: String,
    val address: String,
    val problem_description: String,
    val status: String = "Pending",
    val customer_id: Long,
    val provider_id: Long,
    val service_id: Long
)