package com.capstone.fotokui.ui.favoritephotographer

import android.content.Context
import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.capstone.fotokui.*
import com.capstone.fotokui.domain.Photographer

class EpoxyFavoritePhotographerController(private val context: Context) : TypedEpoxyController<List<Photographer>>() {
    override fun buildModels(data: List<Photographer>?) {
        val titleToolbar = context.getString(R.string.favorite_photographer_title)
        toolbar {
            id("toolbar_favorite_photographer")
            title(titleToolbar)
        }
        findPhotographerSearchInput {
            id("search_favorite_photographer")
            onclick(View.OnClickListener {  })
        }
        val title = context.getString(R.string.favorite_photographer)
        findPhotographerNearbyPhotographerTitle {
            id("favorite_photographer_title")
            title(title)
        }
        data?.forEach { photographer ->
            if (photographer.promo != null && photographer.promo > 0) {
                photographerPromo {
                    id(photographer.id)
                    photographer(photographer)
                }
            } else {
                photographer {
                    id(photographer.id)
                    photographer(photographer)
                }
            }
        }
    }
}