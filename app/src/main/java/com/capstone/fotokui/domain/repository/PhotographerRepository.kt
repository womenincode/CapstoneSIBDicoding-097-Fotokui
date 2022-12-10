package com.capstone.fotokui.domain.repository

import com.capstone.fotokui.domain.Photographer
import com.capstone.fotokui.domain.Response
import kotlinx.coroutines.flow.Flow

interface PhotographerRepository {

    fun getPhotographers(): Flow<List<Photographer>>

    fun getPhotographer(photographerId: String): Flow<Response<Photographer>>

    suspend fun updatePhotographer(photographer: Photographer): Flow<Response<String>>
}