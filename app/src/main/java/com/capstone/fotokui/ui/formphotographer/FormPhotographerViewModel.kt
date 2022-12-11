package com.capstone.fotokui.ui.formphotographer

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
class FormPhotographerViewModel @Inject constructor(
    private val formPhotographerRepository: PhotographerRepository
) : ViewModel() {

    private val _currentPhotographer = MutableLiveData<Response<Photographer>>()
    val currentPhotographer: LiveData<Response<Photographer>> get() = _currentPhotographer

    private val _updatePhotographerState = MutableLiveData<Response<String>>()
    val updatePhotographerState: LiveData<Response<String>> get() = _updatePhotographerState

    fun getPhotographer(id: String) {
        viewModelScope.launch {
            formPhotographerRepository.getPhotographer(id).collectLatest { response ->
                _currentPhotographer.value = response
            }
        }
    }

    fun updatePhotographer(
        id: String,
        photo: String,
        name: String,
        email: String,
        experience: Float,
        yearOrMonthExperience: String,
        price: Int,
        promo: Float,
        phone: String,
        description: String,
        location: String,
        photos: List<String>
    ) {
        viewModelScope.launch {
            val photographer = Photographer(
                id = id,
                photo = photo,
                name = name,
                email = email,
                experience = experience,
                yearOrMonthExperience = yearOrMonthExperience,
                price = price.toFloat(),
                promo = promo,
                phone = phone,
                description = description,
                location = location,
                photos = photos
            )
            formPhotographerRepository.updatePhotographer(photographer).collectLatest{ response ->
                _updatePhotographerState.value = response
            }
        }
    }

}