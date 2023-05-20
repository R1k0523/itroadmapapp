package ru.boringowl.itroadmap.ui.features.auth.state

import ru.boringowl.itroadmap.ui.base.state.UiEvent


sealed class AuthEvent : UiEvent {
    object Init : AuthEvent()
    class SetUsername(val username: String) : AuthEvent()
    class SetPassword(val password: String) : AuthEvent()
    class SetPasswordVisibility(val visible: Boolean) : AuthEvent()
    object Authenticate : AuthEvent()
}