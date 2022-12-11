package com.capstone.fotokui.domain

data class Photographer(
    val id: String? = null,
    val photo: String? = null,
    val name: String? = null,
    val email: String? = null,
    val experience: Float? = null,
    val yearOrMonthExperience: String? = null,
    val price: Float? = null,
    val promo: Float? = null,
    val phone: String? = null,
    val description: String? = null,
    val location: String? = null,
    val distance: String? = null,
    val photos: List<String> = emptyList(),
    val isFavorite: Boolean = false,
    val lat: Double? = null,
    val lon: Double? = null,
)
