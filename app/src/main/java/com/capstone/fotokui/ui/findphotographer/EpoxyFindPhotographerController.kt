package com.capstone.fotokui.ui.findphotographer

import android.content.Context
import android.view.View
import com.airbnb.epoxy.TypedEpoxyController
import com.capstone.fotokui.*
import com.capstone.fotokui.domain.Photographer

class EpoxyFindPhotographerController(private val context: Context) : TypedEpoxyController<List<Photographer>>() {
    override fun buildModels(data: List<Photographer>?) {
        toolbar {
            id("toolbar_find_photographer")
            title("Temukan Fotografer")
        }
        findPhotographerSearchInput {
            id("search_nearby_photographer")
            onclick(View.OnClickListener {  })
        }
        val title = context.getString(R.string.nearby_photographer)
        findPhotographerNearbyPhotographerTitle {
            id("nearby_photographer_title")
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