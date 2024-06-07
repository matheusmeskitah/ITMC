package com.meskitah.itmc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.meskitah.itmc.presentation.navigation.FlickerRoute
import com.meskitah.itmc.presentation.navigation.addFlickerGraph
import com.meskitah.itmc.ui.theme.ITMCTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ITMCTheme {
                val navController = rememberNavController()
                val snackbarState = remember { SnackbarHostState() }

                NavHost(
                    navController = navController,
                    startDestination = FlickerRoute.SportsHomeRoute.route
                ) {
                    addFlickerGraph(navController, snackbarState)
                }
            }
        }
    }
}