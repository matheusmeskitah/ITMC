package com.meskitah.itmc.presentation.flicker_screen

import android.content.Context
import com.meskitah.itmc.domain.model.FlickerImage

sealed class FlickerEvent {
    data class OnLoadImages(val query: String, val context: Context) : FlickerEvent()
    data class OnReloadImages(val context: Context) : FlickerEvent()
    data class OnImageClick(val image: FlickerImage) : FlickerEvent()
}