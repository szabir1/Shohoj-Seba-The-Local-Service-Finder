package com.example.shohojseba.data.model

import kotlinx.serialization.Serializable


@Serializable
data class Customer(

    val customer_id: Long? = null,

    val auth_user_id: String,

    val name: String,

    val phone: String,

    val email: String


)