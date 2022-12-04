package com.capstone.fotokui.repository

import com.capstone.fotokui.utils.ResponseResult
import com.capstone.fotokui.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override val currentUser: FirebaseUser?
    get() = firebaseAuth.currentUser

    override suspend fun login(email: String, password: String): ResponseResult<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            ResponseResult.Success(result.user!!)
        } catch (e: Exception){
            e.printStackTrace()
            ResponseResult.Failure(e)
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): ResponseResult<FirebaseUser> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(name).build())
            ResponseResult.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            ResponseResult.Failure(e)
        }
    }
    override fun logout() {
        firebaseAuth.signOut()
    }
}