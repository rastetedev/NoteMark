package com.raulastete.notemark.di

import com.raulastete.notemark.data.preferences.EncryptedSessionStorage
import com.raulastete.notemark.data.remote.service.ktor.HttpClientFactory
import com.raulastete.notemark.data.repository.DefaultAuthorizationRepository
import com.raulastete.notemark.data.repository.DefaultNoteRepository
import com.raulastete.notemark.domain.SessionStorage
import com.raulastete.notemark.domain.repository.AuthorizationRepository
import com.raulastete.notemark.domain.repository.NoteRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClientFactory(
            sessionStorage = get()
        ).build()
    }
    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()
    singleOf(::DefaultAuthorizationRepository).bind<AuthorizationRepository>()
    singleOf(::DefaultNoteRepository).bind<NoteRepository>()
}