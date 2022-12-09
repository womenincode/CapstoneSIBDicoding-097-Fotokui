package com.capstone.fotokui.domain

sealed interface Response<out T> {
    data class Success<out T>(val result:T): Response<T>
    data class Failure(val message: String): Response<Nothing>
    object Loading : Response<Nothing>
}
