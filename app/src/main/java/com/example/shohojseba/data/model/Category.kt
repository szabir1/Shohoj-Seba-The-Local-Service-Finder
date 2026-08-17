package com.example.shohojseba.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val category_id: Long,
    val category_name: String
)