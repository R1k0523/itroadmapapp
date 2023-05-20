package ru.boringowl.itroadmap.ui.components.bar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.boringowl.itroadmap.ui.base.lists.resetScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseSearchTopBar(
    isSearchOpened: Boolean,
    onSearchOpenedEdit: (Boolean) -> Unit,
    searchText: String,
    onSearchTextEdit: (String) -> Unit,
    @StringRes titleRes: Int,
    title: String? = null,
) {
    val icon = if (isSearchOpened) Icons.Rounded.Close else Icons.Rounded.Search
    TopAppBar(title = {
        if (isSearchOpened)
            OutlinedTextField(
                value = searchText,
                onValueChange = { onSearchTextEdit(it) },
                singleLine = true,
                modifier = Modifier
                    .height(50.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
            )
        else {
            Text(
                text = title ?: stringResource(titleRes),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }, actions = {
        val tag = stringResource(titleRes)
        IconButton(onClick = {
            onSearchOpenedEdit(!isSearchOpened)
            if (searchText.isNotEmpty()) {
                onSearchTextEdit("")
                resetScroll(tag)
            }
        }) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    })
}