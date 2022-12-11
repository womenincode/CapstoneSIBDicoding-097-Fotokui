package com.capstone.fotokui.ui.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.fotokui.domain.Response
import com.capstone.fotokui.domain.Role
import com.capstone.fotokui.domain.User
import com.capstone.fotokui.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _currentUser = MutableLiveData<Response<User>>()
    val currentUser: LiveData<Response<User>> get() = _currentUser

    private val _updateUserState = MutableLiveData<Response<String>>()
    val updateUserState: LiveData<Response<String>> get() = _updateUserState

    fun getCurrentUser() {
        viewModelScope.launch {
            authRepository.currentUser.collectLatest { user ->
                _currentUser.value = user
            }
        }
    }

    fun updateUser(
        id: String,
        photo: File?,
        name: String,
        phone: String,
        location: String,
        role: Role
    ) {
        viewModelScope.launch {
            authRepository.updateUser(id, photo, name, phone, location, role).collectLatest { response ->
                _updateUserState.value = response
            }
        }
    }
}