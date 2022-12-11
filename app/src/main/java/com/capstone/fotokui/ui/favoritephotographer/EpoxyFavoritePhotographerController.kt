package com.capstone.fotokui.ui.favoritephotographer

import android.content.Context
import com.airbnb.epoxy.TypedEpoxyController
import com.capstone.fotokui.*
import com.capstone.fotokui.domain.Photographer
import com.capstone.fotokui.ui.home.EpoxyHomeController

class EpoxyFavoritePhotographerController(private val context: Context, private val onPhotographerListener: EpoxyHomeController.OnPhotographerListener) : TypedEpoxyController<FavoritePhotographerScreenUiState>() {
    override fun buildModels(data: FavoritePhotographerScreenUiState?) {
        val titleToolbar = context.getString(R.string.favorite_photographer_title)
        toolbar {
            id("toolbar_favorite_photographer")
            title(titleToolbar)
        }
        findPhotographerSearchInput {
            id("search_favorite_photographer")
        }
        val title = context.getString(R.string.favorite_photographer)
        findPhotographerNearbyPhotographerTitle {
            id("favorite_photographer_title")
            title(title)
        }
        data?.favoritePhotographers?.forEach { photographer ->
            addItemPhotographer(photographer, onPhotographerListener)
        }
    }

    private fun addItemPhotographer(photographer: Photographer, onPhotographerListener: EpoxyHomeController.OnPhotographerListener) {
        if (photographer.promo != null && photographer.promo > 0) {
            photographerPromo {
                id(photographer.id)
                photographer(photographer)
                onclick(onPhotographerListener)
            }
        } else {
            photographer {
                id(photographer.id)
                photographer(photographer)
                onclick(onPhotographerListener)
            }
        }
    }
}