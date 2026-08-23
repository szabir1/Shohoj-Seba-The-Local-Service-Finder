package com.example.shohojseba.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Review(

    @SerialName("review_id")
    val reviewId: Long = 0,

    @SerialName("created_at")
    val createdAt: String? = null,

    val rating: Int = 0,

    val comment: String? = null,

    @SerialName("customer_id")
    val customerId: Long = 0,

    @SerialName("provider_id")
    val providerId: Long = 0,

    @SerialName("booking_id")
    val bookingId: Long = 0
)