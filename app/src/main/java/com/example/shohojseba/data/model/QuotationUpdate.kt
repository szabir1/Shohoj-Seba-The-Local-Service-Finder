package com.example.shohojseba.data.model


data class QuotationUpdate(

    val quoted_price: Double,

    val quotation_message: String,

    val status: String = "Quotation Sent"

)