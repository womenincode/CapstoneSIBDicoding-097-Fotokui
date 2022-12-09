package com.capstone.fotokui.data

import com.capstone.fotokui.domain.Response
import com.capstone.fotokui.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val currentUser get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): Flow<Response<FirebaseUser>> = flow {
        emit(Response.Loading)
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Response.Success(result.user!!))
        } catch (e: Exception){
            e.printStackTrace()
            emit(Response.Failure(e.localizedMessage as String))
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<Response<FirebaseUser>> = flow {
        emit(Response.Loading)
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())
            emit(Response.Success(result.user!!))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response.Failure(e.localizedMessage as String))
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}