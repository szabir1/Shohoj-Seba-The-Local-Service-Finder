package com.example.shohojseba.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ReviewRequest(

    val rating: Int,

    val comment: String?,

    val customer_id: Long,

    val provider_id: Long,

    val booking_id: Long
)