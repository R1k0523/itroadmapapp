package ru.boringowl.itroadmap.ui.features.profileedit.state

import ru.boringowl.itroadmap.ui.base.state.UiEvent


sealed class ProfileEditEvent : UiEvent {
    object Init : ProfileEditEvent()
    object UpdateUser : ProfileEditEvent()
    class SetUser(val user: UserInfo) : ProfileEditEvent()

    class SetPassword(val passwordForm: PasswordForm) : ProfileEditEvent()
    object UpdatePassword : ProfileEditEvent()

    object GetCode : ProfileEditEvent()
    class SetCode(val code: String) : ProfileEditEvent()
    object SendCode : ProfileEditEvent()
}