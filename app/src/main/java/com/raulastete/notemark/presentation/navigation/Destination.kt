package com.raulastete.notemark.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable
    object Landing : Destination

    @Serializable
    object Login : Destination

    @Serializable
    object Registration : Destination

    @Serializable
    object Home : Destination

    @Serializable
    data class NoteForm(val noteId: String?) : Destination

    @Serializable
    object Settings : Destination
}