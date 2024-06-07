package com.meskitah.itmc.presentation.flicker_screen

import com.meskitah.itmc.domain.model.FlickerImage

data class FlickerState(
    val flickerImage: FlickerImage? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
