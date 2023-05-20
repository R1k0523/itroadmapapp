package ru.boringowl.itroadmap.ui.components.button

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun ButtonContent(
    @StringRes stringRes: Int?,
    painter: Painter? = null,
    @StringRes contentDesRes: Int? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        painter?.let {
            Icon(
                painter = it,
                contentDescription = contentDesRes?.let { stringResource(id = contentDesRes) },
                modifier = Modifier.size(24.dp)
            )
        }
        stringRes?.let {
            Text(
                text = stringResource(id = it),
                style = MaterialTheme.typography.labelLarge.copy(textAlign = TextAlign.Center)
            )
        }
    }
}
