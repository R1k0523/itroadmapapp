package ru.boringowl.itroadmap.ui.features.register.state

import ru.boringowl.itroadmap.ui.base.state.UiState
import ru.boringowl.itroadmap.ui.extensions.UIError


data class RegisterState(
    override val error: UIError? = null,
    val form: RegisterForm = RegisterForm(),
    val passwordVisible: Boolean = false,
    val passwordRepeatVisible: Boolean = false,
    override val loading: Boolean = false,
    val success: Boolean = false,
) : UiState


data class RegisterForm(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val passwordRepeat: String = "",
)