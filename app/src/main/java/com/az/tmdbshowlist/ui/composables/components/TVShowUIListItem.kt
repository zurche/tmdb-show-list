package com.az.tmdbshowlist.ui.composables.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.az.tmdbshowlist.ui.model.TVShowUI

@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
fun TVShowUIListItem(tvShow: TVShowUI = TVShowUI(1, "Breaking Bad", "")) {
    Card(modifier = Modifier.padding(8.dp)) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomStart
        ) {
            AsyncImage(
                modifier = Modifier.matchParentSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(tvShow.imagePath)
                    .crossfade(true)
                    .build(),
                contentDescription = "TV Show Poster",
                contentScale = ContentScale.Crop,
            )

            Box(
                Modifier
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clip(CircleShape)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = tvShow.showName,
                    color = Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }
}