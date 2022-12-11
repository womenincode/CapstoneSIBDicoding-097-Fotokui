package com.capstone.fotokui.domain.repository

import com.capstone.fotokui.domain.Photographer
import com.capstone.fotokui.domain.Response
import kotlinx.coroutines.flow.Flow

interface PhotographerRepository {

    fun getPhotographers(promotionPhotographers: Boolean): Flow<Response<List<Photographer>>>

    fun getPhotographer(photographerId: String): Flow<Response<Photographer>>

    fun getFavoritePhotographers(userId: String): Flow<Response<List<Photographer>>>

    fun searchPhotographers(query: String, photographers: List<Photographer>): Flow<Response<List<Photographer>>>

    suspend fun updatePhotographer(photographer: Photographer): Flow<Response<String>>
}