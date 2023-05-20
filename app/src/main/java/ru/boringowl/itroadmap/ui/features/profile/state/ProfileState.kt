package ru.boringowl.itroadmap.ui.features.profile.state

import ru.boringowl.itroadmap.domain.model.user.User
import ru.boringowl.itroadmap.ui.base.state.UiState
import ru.boringowl.itroadmap.ui.extensions.UIError


data class ProfileState(
    val user: User? = null,
    override val loading: Boolean = false,
    override val error: UIError? = null,
) : UiState
