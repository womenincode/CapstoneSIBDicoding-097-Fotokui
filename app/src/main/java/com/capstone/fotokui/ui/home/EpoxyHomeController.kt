package com.capstone.fotokui.ui.home

import com.airbnb.epoxy.EpoxyController
import com.capstone.fotokui.homeHeader
import com.capstone.fotokui.homeMenu
import com.capstone.fotokui.homePromotionTitle

class EpoxyHomeController : EpoxyController() {
    override fun buildModels() {
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