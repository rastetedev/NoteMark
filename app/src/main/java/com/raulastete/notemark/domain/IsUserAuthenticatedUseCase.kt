package com.raulastete.notemark.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IsUserAuthenticatedUseCase(
    private val sessionStorage: SessionStorage,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(): Boolean {
        return withContext(dispatcher) {
            val authInfo = sessionStorage.get()
            authInfo != null
        }
    }
}