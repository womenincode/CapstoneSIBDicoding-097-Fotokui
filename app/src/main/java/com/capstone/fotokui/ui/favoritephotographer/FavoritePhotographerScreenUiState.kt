package com.capstone.fotokui.ui.favoritephotographer

import com.capstone.fotokui.domain.Photographer

data class FavoritePhotographerScreenUiState(
    val favoritePhotographers: List<Photographer> = emptyList()
)
