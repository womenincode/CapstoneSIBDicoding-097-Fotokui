package com.capstone.fotokui.domain

data class User(
    val id: String,
    val photo: String,
    val name: String,
    val email: String,
    val role: String,
    val lat: Double = 0.0,
    val lon: Double = 0.0
)
