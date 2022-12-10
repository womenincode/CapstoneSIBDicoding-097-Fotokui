package com.capstone.fotokui.domain

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ProfileActivity(
    @DrawableRes val icon: Int,
    @StringRes val title: Int
)
