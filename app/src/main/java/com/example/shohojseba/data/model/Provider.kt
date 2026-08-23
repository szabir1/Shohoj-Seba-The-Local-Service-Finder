package com.example.shohojseba.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Provider(

    val provider_id: Long? = null,

    val auth_user_id: String? = null,

    val name: String,

    val phone: String,

    val email: String,

    val experience: Int,

    val account_status: String = "ACTIVE"

)