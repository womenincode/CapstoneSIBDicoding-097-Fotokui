package com.capstone.fotokui.domain

data class User(
    val id: String? = null,
    val photo: String? = null,
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val location: String? = null,
    val role: String? = null,
    val lat: Double? = null,
    val lon: Double? = null
)
