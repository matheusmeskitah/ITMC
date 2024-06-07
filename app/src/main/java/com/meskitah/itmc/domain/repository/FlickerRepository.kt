package com.meskitah.itmc.domain.repository

import android.content.Context
import com.meskitah.itmc.domain.model.FlickerImage

interface FlickerRepository {

    suspend fun getImages(query: String, context: Context): Result<FlickerImage>
}