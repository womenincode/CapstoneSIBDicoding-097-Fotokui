package com.capstone.fotokui.domain

data class Photographer(
    val id: String,
    val photo: String,
    val name: String,
    val experience: String,
    val yearOrMonthExperience: String,
    val price: Long,
    val promo: Float,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
)
