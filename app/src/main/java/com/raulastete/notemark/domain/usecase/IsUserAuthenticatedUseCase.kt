package com.raulastete.notemark.domain.usecase

import com.raulastete.notemark.domain.SessionStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IsUserAuthenticatedUseCase(
    private val sessionStorage: SessionStorage,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(): Boolean {
        return withContext(dispatcher) {
            val authInfo = sessionStorage.get()
            authInfo != null
        }
    }
}