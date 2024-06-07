package com.meskitah.itmc.data.remote.dto

data class FlickerImageDTO(
    val description: String?,
    val generator: String?,
    val items: List<ItemDTO>?,
    val link: String?,
    val modified: String?,
    val title: String?
)