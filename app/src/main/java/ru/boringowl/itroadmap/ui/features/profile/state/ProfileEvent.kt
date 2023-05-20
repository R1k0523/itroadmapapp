package ru.boringowl.itroadmap.ui.features.profile.state

import ru.boringowl.itroadmap.ui.base.state.UiEvent
import java.io.File


sealed class ProfileEvent : UiEvent {
    object Init : ProfileEvent()
    object Logout : ProfileEvent()
    object FetchMe : ProfileEvent()
    class UploadProfileImage(val file: File) : ProfileEvent()
}