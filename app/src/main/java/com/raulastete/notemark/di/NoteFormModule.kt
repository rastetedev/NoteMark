package com.raulastete.notemark.di

import com.raulastete.notemark.presentation.screens.note_form.NoteFormViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val noteFormModule = module {
    viewModelOf(::NoteFormViewModel)

}