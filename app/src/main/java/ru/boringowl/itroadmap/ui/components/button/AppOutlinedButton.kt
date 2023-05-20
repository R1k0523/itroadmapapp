package ru.boringowl.itroadmap.ui.components.button

import androidx.annotation.StringRes
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun AppOutlinedButton(
    onClick: () -> Unit,
    @StringRes stringRes: Int,
    modifier: Modifier = Modifier,
    painter: Painter? = null,
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    @StringRes contentDesRes: Int? = null,
) {
    OutlinedButton(
        onClick = onClick, shape = MaterialTheme.shapes.medium, modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = textColor)
    ) {
        ButtonContent(
            stringRes,
            painter,
            contentDesRes,
        )
    }
}
