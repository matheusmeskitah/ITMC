package com.meskitah.itmc.domain.model

data class FlickerImage(
    val description: String?,
    val generator: String?,
    val items: List<Item>?,
    val link: String?,
    val modified: String?,
    val title: String?
)