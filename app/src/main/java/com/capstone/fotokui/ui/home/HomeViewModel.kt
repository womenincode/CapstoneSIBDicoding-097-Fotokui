package com.capstone.fotokui.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.fotokui.domain.Response
import com.capstone.fotokui.domain.repository.AuthRepository
import com.capstone.fotokui.domain.repository.PhotographerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val photographerRepository: PhotographerRepository
) : ViewModel() {

    private val _homeScreenUiState = MutableLiveData(HomeScreenUiState())
    val homeScreenUiState: LiveData<HomeScreenUiState> get() = _homeScreenUiState

    fun getCurrentUser() {
        viewModelScope.launch {
            authRepository.currentUser.collectLatest { response ->
                if (response is Response.Success) {
                    _homeScreenUiState.value = _homeScreenUiState.value?.copy(
                        user = response.result
                    )
                }
            }
        }
    }

    fun getPromotionPhotographers() {
        viewModelScope.launch {
            photographerRepository.getPhotographers(promotionPhotographers = true).collectLatest { response ->
                if (response is Response.Success) {
                    _homeScreenUiState.value = _homeScreenUiState.value?.copy(
                        promoPhotographers = response.result
                    )
                }
            }
        }
    }
}