package com.meskitah.itmc.presentation.navigation

sealed class FlickerRoute(val route: String) {
    data object SportsHomeRoute: FlickerRoute("flicker_home")
}