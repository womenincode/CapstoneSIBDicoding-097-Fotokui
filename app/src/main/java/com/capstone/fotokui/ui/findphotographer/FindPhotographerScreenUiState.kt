package com.capstone.fotokui.ui.findphotographer

import com.capstone.fotokui.domain.Photographer

data class FindPhotographerScreenUiState(
    val nearbyPhotographers: List<Photographer> = emptyList()
)
