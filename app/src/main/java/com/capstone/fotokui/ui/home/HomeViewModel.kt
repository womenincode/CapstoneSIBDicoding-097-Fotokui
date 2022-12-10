package com.capstone.fotokui.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.fotokui.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _homeScreenUiState = MutableLiveData<HomeScreenUiState>()
    val homeScreenUiState: LiveData<HomeScreenUiState> get() = _homeScreenUiState

    fun getCurrentUser() {
        viewModelScope.launch {
            _homeScreenUiState.value = authRepository.currentUser?.let {
                HomeScreenUiState(
                    user = it
                )
            }
        }
    }
}