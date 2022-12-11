package com.capstone.fotokui.ui.favoritephotographer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.fotokui.domain.Response
import com.capstone.fotokui.domain.repository.PhotographerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritePhotographerViewModel @Inject constructor(
    private val photographerRepository: PhotographerRepository
) : ViewModel() {

    private val _favoritePhotographerScreenUiState = MutableLiveData<FavoritePhotographerScreenUiState>()
    val favoritePhotographerScreenUiState: LiveData<FavoritePhotographerScreenUiState> get() = _favoritePhotographerScreenUiState

    fun getFavoritePhotographerScreenUiState(id: String) {
        viewModelScope.launch {
            photographerRepository.getFavoritePhotographers(id).collectLatest { response ->
                if (response is Response.Success) {
                    _favoritePhotographerScreenUiState.value = FavoritePhotographerScreenUiState(
                        favoritePhotographers = response.result
                    )

                }
            }
        }
    }

}