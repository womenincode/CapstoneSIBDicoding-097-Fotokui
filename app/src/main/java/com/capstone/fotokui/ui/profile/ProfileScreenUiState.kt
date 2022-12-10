package com.capstone.fotokui.ui.profile

import com.capstone.fotokui.domain.ProfileActivity
import com.capstone.fotokui.domain.User

data class ProfileScreenUiState(
    val user: User? = null,
    val activities: List<ProfileActivity> = emptyList()
)
