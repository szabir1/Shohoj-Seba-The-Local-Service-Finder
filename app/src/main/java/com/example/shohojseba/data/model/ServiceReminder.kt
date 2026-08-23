package com.example.shohojseba.data.model

import kotlinx.serialization.Serializable


@Serializable
data class ServiceReminder(

    val reminder_id: Long? = null,

    val created_at: String? = null,

    val customer_id: Long,

    val provider_id: Long? = null,

    val service_id: Long,

    val booking_id: Long,

    val service_name: String,

    val completed_date: String,

    val next_service_date: String,

    val reminder_status: String = "ACTIVE"

)