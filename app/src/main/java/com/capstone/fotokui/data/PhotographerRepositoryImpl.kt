package com.capstone.fotokui.data

import com.capstone.fotokui.domain.Photographer
import com.capstone.fotokui.domain.Response
import com.capstone.fotokui.domain.repository.PhotographerRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PhotographerRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    firebaseStorage: FirebaseStorage
) : PhotographerRepository {

    private val storageReference = firebaseStorage.reference

    override fun getPhotographers(): Flow<List<Photographer>> = flow {  }

    override suspend fun updatePhotographer(photographer: Photographer): Flow<Response<Photographer>> = flow {
        emit(Response.Loading)
        try {
            firebaseFirestore.collection("photographers").document(photographer.id)
                .set(photographer).await()
            emit(Response.Success(photographer))
        } catch (exception: Exception) {
            emit(Response.Failure(exception.localizedMessage as String))
        }
    }
}