package com.raulastete.notemark.di

import com.raulastete.notemark.domain.usecase.FormatNoteDateUseCase
import com.raulastete.notemark.domain.usecase.FormatUsernameInitialsUseCase
import com.raulastete.notemark.domain.usecase.IsUserAuthenticatedUseCase
import com.raulastete.notemark.presentation.screens.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeViewModel)
    factory {
        FormatUsernameInitialsUseCase(get())
    }

    factory {
        IsUserAuthenticatedUseCase(get())
    }

    factory {
        FormatNoteDateUseCase()
    }
}