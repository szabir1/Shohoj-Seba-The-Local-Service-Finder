package com.example.shohojseba.data.model


import kotlinx.serialization.Serializable


@Serializable
data class AddServiceRequest(

    val service_name: String,

    val description: String,

    val price: Double,

    val duration: String,

    val provider_id: Long,

    val category_id: Long

)