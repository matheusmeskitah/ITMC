package com.meskitah.itmc.data.remote

import com.meskitah.itmc.data.remote.dto.FlickerImageDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickerApi {

    @GET("photos_public.gne")
    suspend fun getImages(
        @Query("format") format: String = "json",
        @Query("nojsoncallback") noJsonCallback: Int = 1,
        @Query("tags") tags: String,
    ): FlickerImageDTO

    companion object {
        const val BASE_URL = "https://api.flickr.com/services/feeds/"
    }
}