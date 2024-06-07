package com.meskitah.itmc.domain.model

data class Item(
    val author: String?,
    val dateTaken: String?,
    val description: String?,
    val link: String?,
    val media: Media?,
    val published: String?,
    val tags: String?,
    val title: String?
)