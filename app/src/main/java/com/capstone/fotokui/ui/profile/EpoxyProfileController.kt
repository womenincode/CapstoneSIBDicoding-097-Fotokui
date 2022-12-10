package com.capstone.fotokui.ui.profile

import android.content.Context
import com.airbnb.epoxy.TypedEpoxyController
import com.capstone.fotokui.*
import com.capstone.fotokui.domain.ProfileActivity

class EpoxyProfileController(private val context: Context, private val onProfileActivityListener: OnProfileActivityListener) : TypedEpoxyController<ProfileScreenUiState>() {

    override fun buildModels(data: ProfileScreenUiState?) {
        val title = context.getString(R.string.profile)
        toolbar {
            id("toolbar_profile")
            title(title)
        }
        profileHeader {
            id("profile_header")
            user(data?.user)
        }
        profileActivityTitle {
            id("profile_activity_title")
        }
        data?.activities?.forEach { profileActivity ->
            addProfileActivity(profileActivity, onProfileActivityListener)
        }
    }

    private fun addProfileActivity(
        profileActivity: ProfileActivity,
        onProfileActivityListener: OnProfileActivityListener
    ) {
        profileActivity {
            id(profileActivity.title)
            profileActivity(profileActivity)
            onClick(onProfileActivityListener)
        }
    }

    interface OnProfileActivityListener {
        fun onClick(profileActivity: ProfileActivity)
    }
}