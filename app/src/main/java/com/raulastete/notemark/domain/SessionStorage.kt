package com.raulastete.notemark.domain

import com.raulastete.notemark.domain.entity.AuthInfo

interface SessionStorage {
    suspend fun get(): AuthInfo?
    suspend fun set(info: AuthInfo?)
}