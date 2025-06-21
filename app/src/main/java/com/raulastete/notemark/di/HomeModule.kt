package com.raulastete.notemark.di

import com.raulastete.notemark.domain.FormatUsernameInitials
import com.raulastete.notemark.domain.IsUserAuthenticatedUseCase
import com.raulastete.notemark.presentation.screens.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeViewModel)
    factory {
        FormatUsernameInitials(get())
    }

    factory {
        IsUserAuthenticatedUseCase(get())
    }
}