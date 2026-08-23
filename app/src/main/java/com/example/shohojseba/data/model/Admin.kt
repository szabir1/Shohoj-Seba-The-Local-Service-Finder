package com.example.shohojseba.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Admin(

    val admin_id: Long,

    val auth_user_id: String,

    val name: String,

    val email: String

)