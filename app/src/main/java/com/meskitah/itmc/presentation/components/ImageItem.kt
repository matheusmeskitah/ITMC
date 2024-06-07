package com.meskitah.itmc.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.meskitah.itmc.domain.model.Item

@Composable
fun ImageItem(
    item: Item
) {
    val rotation = remember { Animatable(0f) }

    Card(
        onClick = {},
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.media?.m)
                    .crossfade(true)
                    .build(),
                contentDescription = item.tags,
                modifier = Modifier.fillMaxWidth().height(200.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = item.title ?: "",
                    style = MaterialTheme.typography.labelLarge,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = item.description ?: "",
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    text = item.author ?: "",
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    text = item.dateTaken ?: "",
                    style = MaterialTheme.typography.labelMedium,
                )
            }
        }
    }
}