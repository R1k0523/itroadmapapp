package ru.boringowl.itroadmap.ui.extensions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Block
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import ru.boringowl.itroadmap.BuildConfig


@Composable
fun AsyncImageById(
    id: String?,
    modifier: Modifier = Modifier,
) {
    SubcomposeAsyncImage(
        "${BuildConfig.BASE_URL}file/$id",
        contentDescription = null,
        modifier = modifier.size(90.dp),
        contentScale = ContentScale.Crop
    ) {
        when(painter.state) {
            is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
            is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
            else -> {
                Column(
                    modifier.background(MaterialTheme.colorScheme.tertiary)
                ) {
                    Icons.Rounded.Block
                }
            }
        }
    }
}


