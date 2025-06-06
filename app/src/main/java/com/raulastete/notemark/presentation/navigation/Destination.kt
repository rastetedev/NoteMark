package com.raulastete.notemark.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {

    @Serializable
    object Landing : Destination

    @Serializable
    object Login : Destination

    @Serializable
    object Registration : Destination
}