package com.capstone.fotokui.domain.repository

import com.capstone.fotokui.domain.Response
import com.capstone.fotokui.domain.Role
import com.capstone.fotokui.domain.User
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import java.io.File

interface AuthRepository {

    val firebaseUser: FirebaseUser?

    val currentUser: Flow<Response<User>>

    fun login(
        email: String,
        password: String
    ): Flow<Response<FirebaseUser>>

    fun register(
        name: String,
        email: String,
        role: Role,
        password: String
    ): Flow<Response<FirebaseUser>>

    fun updateUser(
        id: String,
        photo: File?,
        name: String,
        phone: String,
        location: String,
        role: Role
    ): Flow<Response<String>>

    fun logout()

}