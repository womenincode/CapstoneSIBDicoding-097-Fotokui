package com.capstone.fotokui.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.fotokui.domain.Response
import com.capstone.fotokui.domain.Role
import com.capstone.fotokui.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginLiveData = MutableLiveData<Response<FirebaseUser>?>(null)
    val loginLiveData: LiveData<Response<FirebaseUser>?> = _loginLiveData

    private val _registerLiveData = MutableLiveData<Response<FirebaseUser>?>(null)
    val registerLiveData: LiveData<Response<FirebaseUser>?> = _registerLiveData

    val currentUser get() =  authRepository.firebaseUser

    fun login(email:String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password).collect { response ->
                _loginLiveData.value = response
                delay(500)
                _loginLiveData.value = null
            }
        }
    }

    fun register(name : String, email:String, role: Role, password: String) = viewModelScope.launch {
        authRepository.register(name, email, role, password).collect { response ->
            _registerLiveData.value = response
            delay(500)
            _registerLiveData.value = null
        }
    }

    fun logout() {
        authRepository.logout()
    }
}