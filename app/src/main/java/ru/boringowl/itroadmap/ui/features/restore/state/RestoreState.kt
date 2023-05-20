package ru.boringowl.itroadmap.ui.features.restore.state

import ru.boringowl.itroadmap.ui.base.state.UiState
import ru.boringowl.itroadmap.ui.extensions.UIError


data class RestoreState(
    val form: RestoreForm = RestoreForm(),
    val passwordVisible: Boolean = false,
    val passwordRepeatVisible: Boolean = false,
    val restoreStep: RestoreStep = RestoreStep.Email,
    val token: String = "",
    override val error: UIError? = null,
    override val loading: Boolean = false,
) : UiState


data class RestoreForm(
    val email: String = "",
    val code: String = "",
    val password: String = "",
    val passwordRepeat: String = "",
)


enum class RestoreStep {
    Email, Code, Password, Finish
}