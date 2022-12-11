package com.capstone.fotokui.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.fotokui.R
import com.capstone.fotokui.domain.ProfileActivity
import com.capstone.fotokui.domain.Response
import com.capstone.fotokui.domain.Role
import com.capstone.fotokui.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _profileScreenUiState = MutableLiveData<ProfileScreenUiState>()
    val profileScreenUiState: LiveData<ProfileScreenUiState> get() = _profileScreenUiState

    fun getProfileScreenUiState() {
        viewModelScope.launch {
            authRepository.currentUser.collectLatest { response ->
                if (response is Response.Success) {
                    _profileScreenUiState.value = ProfileScreenUiState(
                        user = response.result,
                        activities = userActivities(response.result.role)
                    )
                }
            }
        }
    }

    private fun userActivities(role: String?): List<ProfileActivity> {
        val userActivities = arrayListOf<ProfileActivity>()

        if (role == Role.FOTOGRAFER.name) {
            userActivities.add(ProfileActivity(R.drawable.ic_camera, R.string.edit_service))
        }

        userActivities.add(ProfileActivity(R.drawable.ic_edit, R.string.edit_profile))
        userActivities.add(ProfileActivity(R.drawable.ic_help, R.string.help))
        userActivities.add(ProfileActivity(R.drawable.ic_logout, R.string.logout))

        return userActivities
    }
}