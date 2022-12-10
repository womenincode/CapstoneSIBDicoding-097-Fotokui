package com.capstone.fotokui.data.di

import com.capstone.fotokui.data.AuthRepositoryImpl
import com.capstone.fotokui.data.PhotographerRepositoryImpl
import com.capstone.fotokui.domain.repository.AuthRepository
import com.capstone.fotokui.domain.repository.PhotographerRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    fun providerFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    fun provideFirebaseFireStore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideFirebaseStorage(): FirebaseStorage = Firebase.storage("gs://fotokui.appspot.com/")

    @Provides
    fun provideAuthRepository(implementation: AuthRepositoryImpl) : AuthRepository = implementation

    @Provides
    fun providePhotographerRepository(implementation: PhotographerRepositoryImpl): PhotographerRepository = implementation

}