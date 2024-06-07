package com.meskitah.itmc.data.mapper

import android.content.Context
import com.meskitah.itmc.data.remote.dto.FlickerImageDTO
import com.meskitah.itmc.data.remote.dto.ItemDTO
import com.meskitah.itmc.data.remote.dto.MediaDTO
import com.meskitah.itmc.domain.model.FlickerImage
import com.meskitah.itmc.domain.model.Item
import com.meskitah.itmc.domain.model.Media
import java.text.SimpleDateFormat
import java.util.Date
import org.jsoup.Jsoup

fun FlickerImageDTO.toFlickerImage(context: Context): FlickerImage {
    return FlickerImage(
        description = description,
        generator = generator,
        items = items?.map { it.toItem(context) },
        link = link,
        modified = modified,
        title = title
    )
}

fun ItemDTO.toItem(context: Context): Item {
    return Item(
        author = author,
        dateTaken = date_taken?.formatToDate(context),
        description = Jsoup.parse(description ?: "").tagName("p").text().substringAfter(": "),
        link = link,
        media = media?.toMedia(),
        published = published,
        tags = tags,
        title = title
    )
}

fun MediaDTO.toMedia(): Media {
    return Media(
        m = m
    )
}

fun String.formatToDate(context: Context): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
    return android.text.format.DateFormat.getMediumDateFormat(context)
        .format(sdf.parse(this) ?: Date())
}