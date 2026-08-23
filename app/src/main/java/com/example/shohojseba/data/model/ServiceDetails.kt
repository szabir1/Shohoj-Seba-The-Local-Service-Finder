package com.example.shohojseba.data.model

import kotlinx.serialization.Serializable


@Serializable
data class ServiceDetails(

    val service_id: Long,

    val created_at: String? = null,

    val service_name: String,

    val description: String? = null,

    val price: Double,

    val duration: String,

    val category_id: Long,

    val provider_id: Long,

    val provider_name: String,

    val provider_phone: String,

    val experience: Int,

    // service_details view may currently return NULL
    val service_status: String? = null,

    // Added locally from Provider table
    val is_verified: Boolean = false,

    // Added locally from Provider table
    val availability_status: String = "AVAILABLE"

)