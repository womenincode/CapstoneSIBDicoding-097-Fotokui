package com.capstone.fotokui.ui.findphotographer

import android.content.Context
import androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged
import com.airbnb.epoxy.TypedEpoxyController
import com.capstone.fotokui.*
import com.capstone.fotokui.domain.Photographer
import com.capstone.fotokui.ui.home.EpoxyHomeController

class EpoxyFindPhotographerController(
    private val context: Context,
    private val onPhotographerListener: EpoxyHomeController.OnPhotographerListener,
    private val onSearchChanged: OnTextChanged
) : TypedEpoxyController<FindPhotographerScreenUiState>() {
    override fun buildModels(data: FindPhotographerScreenUiState?) {
        toolbar {
            id("toolbar_find_photographer")
            title("Temukan Fotografer")
        }

        addItemPhotographerSearchInput(onSearchChanged)

        val title = context.getString(R.string.nearby_photographer)
        findPhotographerNearbyPhotographerTitle {
            id("nearby_photographer_title")
            title(title)
        }
        data?.nearbyPhotographers?.forEach { photographer ->
            addItemPhotographer(photographer, onPhotographerListener)
        }
    }

    private fun addItemPhotographerSearchInput(onSearchChanged: OnTextChanged) {
        findPhotographerSearchInput {
            id("search_nearby_photographer")
            onTextChanged(onSearchChanged)
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