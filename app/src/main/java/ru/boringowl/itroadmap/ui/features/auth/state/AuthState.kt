package ru.boringowl.itroadmap.ui.features.auth.state

import ru.boringowl.itroadmap.ui.base.state.UiState
import ru.boringowl.itroadmap.ui.extensions.UIError


data class AuthState(
    val username: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    override val error: UIError? = null,
    override val loading: Boolean = false
) : UiState
