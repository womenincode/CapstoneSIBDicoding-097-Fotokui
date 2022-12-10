package com.capstone.fotokui.data

import com.capstone.fotokui.domain.Response
import com.capstone.fotokui.domain.Role
import com.capstone.fotokui.domain.User
import com.capstone.fotokui.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    firebaseStorage: FirebaseStorage
) : AuthRepository {

    override val firebaseUser get() = firebaseAuth.currentUser

    override val currentUser: Flow<User> = flow {

        val user = firebaseFirestore.collection("users").document(firebaseUser?.uid.toString())
            .get().await()

        val id = user.getString("id")
        val photo = user.getString("photo")
        val name = user.getString("name")
        val email = user.getString("email")
        val role = user.getString("role")
        val lat = user.getDouble("lat")
        val lon = user.getDouble("lon")

        emit(User(id, photo, name, email, role, lat, lon))
    }

    private val storageReference = firebaseStorage.reference

    override fun login(email: String, password: String): Flow<Response<FirebaseUser>> = flow {
        emit(Response.Loading)
        try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Response.Success(result.user!!))
        } catch (e: Exception){
            e.printStackTrace()
            emit(Response.Failure(e.localizedMessage as String))
        }
    }

    override fun register(
        name: String,
        email: String,
        role: Role,
        password: String
    ): Flow<Response<FirebaseUser>> = flow {
        emit(Response.Loading)
        try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val defaultProfileUrl = storageReference.child("users/profiles/default_profile.png").downloadUrl.await()
            val profileUpdates = userProfileChangeRequest {
                displayName = name
                photoUri = defaultProfileUrl
            }
            result?.user?.updateProfile(profileUpdates)?.await()
            val registeredUser = result.user
            createUser(registeredUser, role)
            if (role == Role.PENGGUNA) {
                firebaseAuth.signOut()
            }
            emit(Response.Success(registeredUser!!))
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Response.Failure(e.localizedMessage as String))
        }
    }

    private suspend fun createUser(registeredUser: FirebaseUser?, role: Role) {
        val user = User(
            id = registeredUser?.uid.toString(),
            photo = registeredUser?.photoUrl.toString(),
            name = registeredUser?.displayName.toString(),
            email = registeredUser?.email.toString(),
            role = role.name
        )
        firebaseFirestore.collection("users").document(user.id.toString()).set(user).await()
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}