package com.meskitah.itmc.domain.use_case

import android.content.Context
import com.meskitah.itmc.domain.model.FlickerImage
import com.meskitah.itmc.domain.repository.FlickerRepository

class GetImages(private val repository: FlickerRepository) {

    suspend operator fun invoke(parameters: String, context: Context): Result<FlickerImage> {
        return repository.getImages(parameters, context)
    }
}