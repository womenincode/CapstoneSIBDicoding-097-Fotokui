package com.capstone.fotokui.utils

//@OptIn(ExperimentalCoroutinesApi::class)
//suspend fun <T> Task<T>.await():T{
//    return suspendCancellableCoroutine {
//        addOnCompleteListener{ task ->
//            if(task.exception == null) {
//                it.resume(task.result, null)
//            } else {
//                it.resumeWithException(task.exception!!)
//            }
//        }
//    }
//}