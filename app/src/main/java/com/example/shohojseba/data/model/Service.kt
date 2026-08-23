package com.example.shohojseba.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Service(

    val service_id: Long,

    val created_at: String? = null,

    val service_name: String,

    val description: String? = null,

    val price: Double,

    val duration: String,

    val provider_id: Long,

    val category_id: Long,

    val service_status: String = "ACTIVE"

)