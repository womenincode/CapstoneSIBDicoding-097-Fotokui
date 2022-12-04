package com.capstone.fotokui.utils

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await():T{
    return suspendCancellableCoroutine {
        addOnCompleteListener{ task ->
            if(task.exception == null) {
                it.resume(task.result, null)
            } else {
                it.resumeWithException(task.exception!!)
            }
        }
    }
}