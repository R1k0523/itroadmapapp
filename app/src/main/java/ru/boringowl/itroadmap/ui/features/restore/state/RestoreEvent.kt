package ru.boringowl.itroadmap.ui.features.restore.state

import ru.boringowl.itroadmap.ui.base.state.UiEvent


sealed class RestoreEvent : UiEvent {
    object Init : RestoreEvent()
    class SetForm(val form: RestoreForm) : RestoreEvent()
    class SetPasswordVisibility(val visible: Boolean) : RestoreEvent()
    class SetPasswordRepeatVisibility(val visible: Boolean) : RestoreEvent()
    object GetCode : RestoreEvent()
    object GetToken : RestoreEvent()
    object SetPassword : RestoreEvent()
}
