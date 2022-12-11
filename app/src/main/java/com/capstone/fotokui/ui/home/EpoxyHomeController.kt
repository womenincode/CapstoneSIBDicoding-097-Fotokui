package com.capstone.fotokui.ui.home

import com.airbnb.epoxy.TypedEpoxyController
import com.capstone.fotokui.domain.Photographer
import com.capstone.fotokui.domain.User
import com.capstone.fotokui.homeHeader
import com.capstone.fotokui.homeMenu
import com.capstone.fotokui.homePromotionTitle
import com.capstone.fotokui.photographerPromo

class EpoxyHomeController(private val onHomeClickListener: OnHomeClickListener, private val onPhotographerListener: OnPhotographerListener) : TypedEpoxyController<HomeScreenUiState>() {
    override fun buildModels(data: HomeScreenUiState?) {
        addItemHomeHeader(data?.user, onHomeClickListener)
        homeMenu {
            id("home menu")
        }
        homePromotionTitle {
            id("home promotion title")
        }
        data?.promoPhotographers?.forEach { photographer ->
            addItemPhotographer(photographer, onPhotographerListener)
        }
    }

    private fun addItemHomeHeader(user: User?, onHomeClickListener: OnHomeClickListener) {
        homeHeader {
            id("home header")
            user(user)
            onHomeClick(onHomeClickListener)
        }
    }

    private fun addItemPhotographer(photographer: Photographer, onPhotographerListener: OnPhotographerListener) {
        photographerPromo {
            id(photographer.id)
            photographer(photographer)
            onclick(onPhotographerListener)
        }
    }

    interface OnHomeClickListener {
        fun onMapClick()
        fun onProfileClick()
    }

    interface OnPhotographerListener {
        fun onClick(photographerId: String)
        fun onFavoriteClick(id: String)
    }
}