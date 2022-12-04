package com.capstone.fotokui.utils

sealed class ResponseResult<out T> {
    data class Success<out T>(val result:T): ResponseResult<T>()
    data class Failure(val exception: Exception):ResponseResult<Nothing>()
    object Loading : ResponseResult<Nothing>()
}
