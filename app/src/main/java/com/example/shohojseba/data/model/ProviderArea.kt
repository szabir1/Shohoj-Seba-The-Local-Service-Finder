package com.example.shohojseba.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProviderArea(

    val provider_area_id: Long = 0,

    val provider_id: Long,

    val area_id: Long
)