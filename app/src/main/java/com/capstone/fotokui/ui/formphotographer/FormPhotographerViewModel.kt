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

    private val _updatePhotographerState = MutableLiveData<Response<String>>()
    val updatePhotographerState: LiveData<Response<String>> get() = _updatePhotographerState

    fun updatePhotographer(
        id: String,
        photo: String,
        name: String,
        email: String,
        experience: Float,
        yearOrMonthExperience: String,
        price: Int,
        promo: Float,
        description: String,
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
                price = price,
                promo = promo,
                description = description,
                photos = photos
            )
            formPhotographerRepository.updatePhotographer(photographer).collectLatest{ response ->
                when (response) {
                    is Response.Loading -> _updatePhotographerState.value = Response.Loading
                    is Response.Success -> _updatePhotographerState.value = Response.Success("Data photographer berhasil di simpan!")
                    is Response.Failure -> _updatePhotographerState.value = response
                }
            }
        }
    }

}