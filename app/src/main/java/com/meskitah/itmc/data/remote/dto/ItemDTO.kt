package com.meskitah.itmc.data.remote.dto

data class ItemDTO(
    val author: String?,
    val author_id: String?,
    val date_taken: String?,
    val description: String?,
    val link: String?,
    val media: MediaDTO?,
    val published: String?,
    val tags: String?,
    val title: String?
)