package com.capstone.fotokui.data

import com.capstone.fotokui.domain.Photographer
import com.capstone.fotokui.domain.Response
import com.capstone.fotokui.domain.repository.PhotographerRepository
import com.capstone.fotokui.utils.DataDummy
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

    override fun getPhotographer(photographerId: String): Flow<Response<Photographer>> = flow {
        emit(Response.Loading)
        try {
            val photographerSnapshot = firebaseFirestore.collection("photographers").document(photographerId).get().await()

            val photographerPhoto = photographerSnapshot.getString("photo")
            val photographerName = photographerSnapshot.getString("name")
            val photographerEmail = photographerSnapshot.getString("email")
            val photographerExperience = photographerSnapshot.get("experience", Float::class.java)
            val photographerYearOrMonthExperience = photographerSnapshot.getString("yearOrMonthExperience")
            val photographerPrice = photographerSnapshot.get("price", Int::class.java)
            val photographerPromo = photographerSnapshot.get("promo", Float::class.java)
            val photographerPhone = photographerSnapshot.getString("phone")
            val photographerDescription = photographerSnapshot.getString("description")
            val photographerLocation = photographerSnapshot.getString("location")
            val photographerPhotos= (photographerSnapshot.get("photos") as List<*>).map { it.toString() }
            val photographerLat = photographerSnapshot.getDouble("lat")
            val photographerLon = photographerSnapshot.getDouble("lon")

            val photographer = Photographer(
                id = photographerId,
                photo = photographerPhoto,
                name = photographerName,
                email = photographerEmail,
                experience = photographerExperience,
                yearOrMonthExperience = photographerYearOrMonthExperience,
                price = photographerPrice?.toFloat(),
                promo = photographerPromo,
                phone = photographerPhone,
                description = photographerDescription,
                location = photographerLocation,
                photos = photographerPhotos,
                distance = DataDummy.generateDummyDistance(),
                lat = photographerLat,
                lon = photographerLon
            )

            emit(Response.Success(photographer))

        } catch (exception: Exception) {
            emit(Response.Failure(exception.localizedMessage as String))
        }
    }

    override fun getFavoritePhotographers(userId: String): Flow<Response<List<Photographer>>> = flow {
        emit(Response.Loading)
        try {
            val photographersSnapshot = firebaseFirestore.collection("photographers")
                .whereEqualTo("id", "dU1NA7hmROSadwqVDq041MS4mhE3")
                .limit(20)
                .get()
                .await()

            val photographers = photographersSnapshot.map { photographerSnapshot ->

                val photographerId = photographerSnapshot.getString("id")
                val photographerPhoto = photographerSnapshot.getString("photo")
                val photographerName = photographerSnapshot.getString("name")
                val photographerEmail = photographerSnapshot.getString("email")
                val photographerExperience = photographerSnapshot.get("experience", Float::class.java)
                val photographerYearOrMonthExperience = photographerSnapshot.getString("yearOrMonthExperience")
                val photographerPrice = photographerSnapshot.get("price", Int::class.java)
                val photographerPromo = photographerSnapshot.get("promo", Float::class.java)
                val photographerPhone = photographerSnapshot.getString("phone")
                val photographerDescription = photographerSnapshot.getString("description")
                val photographerLocation = photographerSnapshot.getString("location")
                val photographerPhotos= (photographerSnapshot.get("photos") as List<*>).map { it.toString() }
                val photographerLat = photographerSnapshot.getDouble("lat")
                val photographerLon = photographerSnapshot.getDouble("lon")

                Photographer(
                    id = photographerId,
                    photo = photographerPhoto,
                    name = photographerName,
                    email = photographerEmail,
                    experience = photographerExperience,
                    yearOrMonthExperience = photographerYearOrMonthExperience,
                    price = photographerPrice?.toFloat(),
                    promo = photographerPromo,
                    phone = photographerPhone,
                    description = photographerDescription,
                    location = photographerLocation,
                    photos = photographerPhotos,
                    distance = DataDummy.generateDummyDistance(),
                    lat = photographerLat,
                    lon = photographerLon
                )
            }
            emit(Response.Success(photographers))
        } catch (exception: Exception) {
            emit(Response.Failure(exception.localizedMessage as String))
        }
    }

    override fun searchPhotographers(query: String, photographers: List<Photographer>): Flow<Response<List<Photographer>>> = flow {
        emit(Response.Loading)
        try {
//            val photographersSnapshot = firebaseFirestore.collection("photographers")
//                .orderBy("name")
//                .startAt(query)
//                .endAt(query)
//                .limit(20)
//                .get()
//                .await()
//
//            val photographers = photographersSnapshot.map { photographerSnapshot ->
//
//                val photographerId = photographerSnapshot.getString("id")
//                val photographerPhoto = photographerSnapshot.getString("photo")
//                val photographerName = photographerSnapshot.getString("name")
//                val photographerEmail = photographerSnapshot.getString("email")
//                val photographerExperience = photographerSnapshot.get("experience", Float::class.java)
//                val photographerYearOrMonthExperience = photographerSnapshot.getString("yearOrMonthExperience")
//                val photographerPrice = photographerSnapshot.get("price", Int::class.java)
//                val photographerPromo = photographerSnapshot.get("promo", Float::class.java)
//                val photographerPhone = photographerSnapshot.getString("phone")
//                val photographerDescription = photographerSnapshot.getString("description")
//                val photographerLocation = photographerSnapshot.getString("location")
//                val photographerPhotos= (photographerSnapshot.get("photos") as List<*>).map { it.toString() }
//                val photographerLat = photographerSnapshot.getDouble("lat")
//                val photographerLon = photographerSnapshot.getDouble("lon")
//
//                Photographer(
//                    id = photographerId,
//                    photo = photographerPhoto,
//                    name = photographerName,
//                    email = photographerEmail,
//                    experience = photographerExperience,
//                    yearOrMonthExperience = photographerYearOrMonthExperience,
//                    price = photographerPrice,
//                    promo = photographerPromo,
//                    phone = photographerPhone,
//                    description = photographerDescription,
//                    location = photographerLocation,
//                    photos = photographerPhotos,
//                    lat = photographerLat,
//                    lon = photographerLon
//                )

//            }
            val searchedPhotographers = photographers.filter { photographer ->
                photographer.name?.contains(query, true) == true
            }
            emit(Response.Success(searchedPhotographers))
        } catch (exception: Exception) {
            emit(Response.Failure(exception.localizedMessage as String))
        }
    }

    override fun getPhotographers(promotionPhotographers: Boolean): Flow<Response<List<Photographer>>> = flow {
        emit(Response.Loading)
        try {
            val photographersSnapshot = if (promotionPhotographers) {
                firebaseFirestore.collection("photographers")
                    .whereGreaterThan("promo", 0)
                    .limit(20)
                    .get()
                    .await()
            } else {
                firebaseFirestore.collection("photographers")
                    .limit(20)
                    .get()
                    .await()
            }

            val photographers = photographersSnapshot.map { photographerSnapshot ->

                val photographerId = photographerSnapshot.getString("id")
                val photographerPhoto = photographerSnapshot.getString("photo")
                val photographerName = photographerSnapshot.getString("name")
                val photographerEmail = photographerSnapshot.getString("email")
                val photographerExperience = photographerSnapshot.get("experience", Float::class.java)
                val photographerYearOrMonthExperience = photographerSnapshot.getString("yearOrMonthExperience")
                val photographerPrice = photographerSnapshot.get("price", Int::class.java)
                val photographerPromo = photographerSnapshot.get("promo", Float::class.java)
                val photographerPhone = photographerSnapshot.getString("phone")
                val photographerDescription = photographerSnapshot.getString("description")
                val photographerLocation = photographerSnapshot.getString("location")
                val photographerPhotos= (photographerSnapshot.get("photos") as List<*>).map { it.toString() }
                val photographerLat = photographerSnapshot.getDouble("lat")
                val photographerLon = photographerSnapshot.getDouble("lon")

                Photographer(
                    id = photographerId,
                    photo = photographerPhoto,
                    name = photographerName,
                    email = photographerEmail,
                    experience = photographerExperience,
                    yearOrMonthExperience = photographerYearOrMonthExperience,
                    price = photographerPrice?.toFloat(),
                    promo = photographerPromo,
                    phone = photographerPhone,
                    description = photographerDescription,
                    location = photographerLocation,
                    photos = photographerPhotos,
                    distance = DataDummy.generateDummyDistance(),
                    lat = photographerLat,
                    lon = photographerLon
                )
            }
            emit(Response.Success(photographers))
        } catch (exception: Exception) {
            emit(Response.Failure(exception.localizedMessage as String))
        }
    }

    override suspend fun updatePhotographer(photographer: Photographer): Flow<Response<String>> = flow {
        emit(Response.Loading)
        try {
            firebaseFirestore.collection("photographers").document(photographer.id.toString())
                .set(photographer).await()
            firebaseFirestore.collection("users").document(photographer.id.toString())
                .update(mapOf("location" to photographer.location))
            emit(Response.Success("Data photographer berhasil di simpan!"))
        } catch (exception: Exception) {
            emit(Response.Failure(exception.localizedMessage as String))
        }
    }
}