package ru.boringowl.itroadmap.ui.features.register.state

import ru.boringowl.itroadmap.ui.base.state.UiEvent


sealed class RegisterEvent : UiEvent {
    object Init : RegisterEvent()
    class SetForm(val form: RegisterForm) : RegisterEvent()
    class SetPasswordVisibility(val visible: Boolean) : RegisterEvent()
    class SetPasswordRepeatVisibility(val visible: Boolean) : RegisterEvent()
    object Register : RegisterEvent()
}
