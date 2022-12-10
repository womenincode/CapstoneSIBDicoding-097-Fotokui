package com.capstone.fotokui.ui.home

import com.airbnb.epoxy.TypedEpoxyController
import com.capstone.fotokui.homeHeader
import com.capstone.fotokui.homeMenu
import com.capstone.fotokui.homePromotionTitle
import com.capstone.fotokui.photographerPromo

class EpoxyHomeController : TypedEpoxyController<HomeScreenUiState>() {
    override fun buildModels(data: HomeScreenUiState?) {
        homeHeader {
            id("home header")
            user(data?.user)
        }
        homeMenu {
            id("home menu")
        }
        homePromotionTitle {
            id("home promotion title")
        }
        data?.promoPhotographers?.forEach { photographer ->
            photographerPromo {
                id(photographer.id)
                photographer(photographer)
            }
        }
    }
}