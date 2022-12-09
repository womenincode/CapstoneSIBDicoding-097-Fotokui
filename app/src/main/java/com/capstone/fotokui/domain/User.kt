package com.capstone.fotokui.domain

data class User(
    val id: String,
    val photo: String,
    val name: String,
    val email: String,
    val role: String,
    val lat: Double,
    val lon: Double
)
