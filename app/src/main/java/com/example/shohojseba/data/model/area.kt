package com.example.shohojseba.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Area(
    val area_id: Long,
    val area_name: String
)