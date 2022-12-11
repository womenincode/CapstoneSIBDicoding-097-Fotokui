package com.capstone.fotokui.ui.home

import com.capstone.fotokui.domain.Photographer
import com.capstone.fotokui.domain.User

data class HomeScreenUiState(
    val user: User = User(),
    val promoPhotographers: List<Photographer> = emptyList()
)
