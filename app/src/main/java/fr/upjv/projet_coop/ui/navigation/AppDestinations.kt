package fr.upjv.projet_coop.ui.navigation

import kotlinx.serialization.Serializable

sealed interface AppDestinations {
    
    @Serializable
    data object Home : AppDestinations

    @Serializable
    data object Feature2 : AppDestinations

    @Serializable
    data object Feature3 : AppDestinations
}
