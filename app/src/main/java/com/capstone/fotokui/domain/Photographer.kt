package com.capstone.fotokui.domain

data class Photographer(
    val id: String,
    val photo: String,
    val name: String,
    val email: String,
    val experience: Float,
    val yearOrMonthExperience: String,
    val price: Int,
    val promo: Float,
    val description: String,
    val photos: List<String>,
    val isFavorite: Boolean = false,
    val lat: Double? = null,
    val lon: Double? = null,
)
