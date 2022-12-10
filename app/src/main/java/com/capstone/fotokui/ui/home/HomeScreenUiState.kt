package com.capstone.fotokui.ui.home

import com.capstone.fotokui.domain.Photographer
import com.google.firebase.auth.FirebaseUser

data class HomeScreenUiState(
    val user: FirebaseUser,
    val promoPhotographers: List<Photographer> = emptyList()
)
