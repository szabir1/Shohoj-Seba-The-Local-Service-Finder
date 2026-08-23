package com.example.shohojseba.data

object UserSession {

    var customerId: Long? = null
    var providerId: Long? = null

    var role: String? = null

    fun clear() {
        customerId = null
        providerId = null
        role = null
    }
}