package com.capstone.fotokui.ui.profile

import android.content.Context
import com.airbnb.epoxy.TypedEpoxyController
import com.capstone.fotokui.*
import com.capstone.fotokui.domain.ProfileActivity

class EpoxyProfileController(private val context: Context) : TypedEpoxyController<List<ProfileActivity>>() {
    override fun buildModels(data: List<ProfileActivity>?) {
        val title = context.getString(R.string.favorite_photographer_title)
        toolbar {
            id("toolbar_profile")
            title(title)
        }
        profileHeader {
            id("profile_header")
        }
        profileActivityTitle {
            id("profile_activity_title")
        }
        data?.forEach { profileActivity ->
            profileActivity {
                id(profileActivity.title)
                profileActivity(profileActivity)
            }
        }
    }
}