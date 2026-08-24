package com.example.shohojseba.data.model

import kotlinx.serialization.Serializable


@Serializable
data class AppNotification(

    val notification_id: Long? = null,

    val created_at: String? = null,

    val customer_id: Long? = null,

    val provider_id: Long? = null,

    val booking_id: Long? = null,

    val title: String,

    val message: String,

    val notification_type: String,

    val is_read: Boolean = false

)