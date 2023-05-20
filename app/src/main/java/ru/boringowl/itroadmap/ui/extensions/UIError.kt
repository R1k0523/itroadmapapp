package ru.boringowl.itroadmap.ui.extensions

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.boringowl.itroadmap.domain.model.BaseResult

class UIError(
    private val message: String? = null,
    @StringRes private val messageId: Int? = null
) {
    @Composable
    fun text() = message ?: messageId?.let { stringResource(it) } ?: ""
}


fun BaseResult.Error.toUI() = UIError(message, messageId)