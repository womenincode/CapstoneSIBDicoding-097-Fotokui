package com.capstone.fotokui.ui.home

import com.airbnb.epoxy.TypedEpoxyController
import com.capstone.fotokui.domain.Photographer
import com.capstone.fotokui.homeHeader
import com.capstone.fotokui.homeMenu
import com.capstone.fotokui.homePromotionTitle

class EpoxyHomeController : TypedEpoxyController<List<Photographer>>() {
    override fun buildModels(data: List<Photographer>?) {
        homeHeader {
            id("home header")
        }
        homeMenu {
            id("home menu")
        }
        homePromotionTitle {
            id("home promotion title")
        }

    }
}