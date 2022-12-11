package com.capstone.fotokui.ui.findphotographer

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
class FindPhotographerViewModel @Inject constructor(
    private val photographerRepository: PhotographerRepository
) : ViewModel() {

    private val _findPhotographerScreenUiState = MutableLiveData<FindPhotographerScreenUiState>()
    val findPhotographerScreenUiState: LiveData<FindPhotographerScreenUiState> get() = _findPhotographerScreenUiState

    fun getFindPhotographerScreenUiState() {
        viewModelScope.launch {
            photographerRepository.getPhotographers(promotionPhotographers = false).collectLatest { response ->
                if (response is Response.Success) {
                    _findPhotographerScreenUiState.value = FindPhotographerScreenUiState(
                        nearbyPhotographers = response.result
                    )

                }
            }
        }
    }

    fun searchPhotographer(query: String) {
        viewModelScope.launch {
            photographerRepository.searchPhotographers(
                query,
                _findPhotographerScreenUiState.value?.nearbyPhotographers ?: emptyList()
            ).collectLatest { response ->
                if (response is Response.Success) {
                    _findPhotographerScreenUiState.value = FindPhotographerScreenUiState(
                        nearbyPhotographers = response.result
                    )

                }
            }
        }
    }

}