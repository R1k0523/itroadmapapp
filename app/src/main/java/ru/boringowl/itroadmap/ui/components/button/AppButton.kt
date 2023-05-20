package ru.boringowl.itroadmap.ui.components.button

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp


@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes stringRes: Int? = null,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    enabled: Boolean = true,
    loading: Boolean = false,
    painter: Painter? = null,
    @StringRes contentDesRes: Int? = null,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier,
        enabled = enabled && !loading
    ) {
        if (loading) {
            CircularProgressIndicator(Modifier.size(24.dp))
        } else {
            ButtonContent(
                stringRes,
                painter,
                contentDesRes,
            )
        }
    }
}
