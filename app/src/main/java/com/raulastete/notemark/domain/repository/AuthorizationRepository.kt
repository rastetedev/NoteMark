package com.raulastete.notemark.domain.repository

import com.raulastete.notemark.domain.DataError
import com.raulastete.notemark.domain.EmptyDataResult

interface AuthorizationRepository {

    suspend fun login(
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network>

    suspend fun registration(
        username: String,
        email: String,
        password: String
    ): EmptyDataResult<DataError.Network>


    suspend fun logout(
        refreshToken: String
    ): EmptyDataResult<DataError.Network>
}