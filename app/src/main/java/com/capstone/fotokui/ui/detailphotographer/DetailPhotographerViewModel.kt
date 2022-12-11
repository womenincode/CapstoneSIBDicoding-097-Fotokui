package com.capstone.fotokui.ui.detailphotographer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.fotokui.domain.Photographer
import com.capstone.fotokui.domain.Response
import com.capstone.fotokui.domain.repository.PhotographerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailPhotographerViewModel @Inject constructor(
    private val formPhotographerRepository: PhotographerRepository
) : ViewModel() {

    private val _currentPhotographer = MutableLiveData<Response<Photographer>>()
    val currentPhotographer: LiveData<Response<Photographer>> get() = _currentPhotographer

    fun getPhotographer(id: String) {
        viewModelScope.launch {
            formPhotographerRepository.getPhotographer(id).collectLatest { response ->
                _currentPhotographer.value = response
            }
        }
    }
}