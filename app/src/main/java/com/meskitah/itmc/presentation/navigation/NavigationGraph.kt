package com.meskitah.itmc.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.meskitah.itmc.presentation.flicker_screen.FlickerScreen

fun NavGraphBuilder.addFlickerGraph(
    navController: NavController,
    snackbarState: SnackbarHostState
) {
    composable(route = FlickerRoute.SportsHomeRoute.route) {
        FlickerScreen(
            snackbarHostState = snackbarState
        )
    }
}