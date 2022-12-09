package com.capstone.fotokui.domain.repository

import com.capstone.fotokui.domain.Response
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    val currentUser: FirebaseUser?

    suspend fun login(
        email: String,
        password: String
    ): Flow<Response<FirebaseUser>>

    suspend fun register(
        name: String,
        email: String,
        role: String,
        password: String
    ): Flow<Response<FirebaseUser>>

    fun logout()

}