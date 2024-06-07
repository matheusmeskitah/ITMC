package com.meskitah.itmc.data.repository

import android.content.Context
import com.meskitah.itmc.data.mapper.toFlickerImage
import com.meskitah.itmc.data.remote.FlickerApi
import com.meskitah.itmc.domain.model.FlickerImage
import com.meskitah.itmc.domain.repository.FlickerRepository

class FlickerRepositoryImpl(private val api: FlickerApi) : FlickerRepository {

    override suspend fun getImages(query: String, context: Context): Result<FlickerImage> {
        return try {
            val flicker = api.getImages(tags = query)

            Result.success(flicker.toFlickerImage(context))
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}