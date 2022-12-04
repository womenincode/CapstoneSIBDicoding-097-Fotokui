package com.capstone.fotokui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.fotokui.repository.AuthRepository
import com.capstone.fotokui.utils.ResponseResult
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _loginLiveData = MutableLiveData<ResponseResult<FirebaseUser>?>(null)
    val loginLiveData: LiveData<ResponseResult<FirebaseUser>?> = _loginLiveData

    private val _registerLiveData = MutableLiveData<ResponseResult<FirebaseUser>?>(null)
    val registerLiveData: LiveData<ResponseResult<FirebaseUser>?> = _registerLiveData

    val currentUser: FirebaseUser?
    get() = authRepository.currentUser

    init {
        if(authRepository.currentUser != null) {
            _loginLiveData.value = ResponseResult.Success(authRepository.currentUser!!)
        }
    }

    fun login(email:String, password: String) = viewModelScope.launch {
        _loginLiveData.value = ResponseResult.Loading
        val result = authRepository.login(email, password)
        _loginLiveData.value = result
    }

    fun register(name : String, email:String, password: String) = viewModelScope.launch {
        _registerLiveData.value = ResponseResult.Loading
        val result = authRepository.register(name, email, password)
        _registerLiveData.value = result
    }

    fun logout() {
        authRepository.logout()
    }
}