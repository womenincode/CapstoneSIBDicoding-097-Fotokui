package com.capstone.fotokui.repository

import com.capstone.fotokui.utils.ResponseResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser: FirebaseUser?
    suspend fun login(email:String, password:String): ResponseResult<FirebaseUser>
    suspend fun register(name: String, email:String, password:String):ResponseResult<FirebaseUser>
    fun logout()
}