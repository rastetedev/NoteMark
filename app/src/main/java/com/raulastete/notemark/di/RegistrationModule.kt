package com.raulastete.notemark.di

import com.raulastete.notemark.presentation.screens.registration.RegistrationViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val registrationModule = module {
    viewModelOf(::RegistrationViewModel)
}