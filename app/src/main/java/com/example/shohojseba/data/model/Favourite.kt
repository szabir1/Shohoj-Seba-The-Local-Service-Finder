package com.example.shohojseba.data.model

import kotlinx.serialization.Serializable


@Serializable
data class Favorite(

    val favorite_id: Long? = null,

    val created_at: String? = null,

    val customer_id: Long,

    val service_id: Long

)