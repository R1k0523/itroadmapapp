package ru.boringowl.itroadmap.ui.features.profileedit.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import ru.boringowl.itroadmap.data.network.api.user.request.UpdatePasswordRequest
import ru.boringowl.itroadmap.data.network.api.user.request.UpdateRequest
import ru.boringowl.itroadmap.data.repository.UserRepository
import ru.boringowl.itroadmap.domain.model.onError
import ru.boringowl.itroadmap.domain.model.onLoading
import ru.boringowl.itroadmap.domain.model.onSuccess
import ru.boringowl.itroadmap.ui.base.viewmodel.BaseViewModel
import ru.boringowl.itroadmap.ui.features.profileedit.state.ProfileEditEffect
import ru.boringowl.itroadmap.ui.features.profileedit.state.ProfileEditEvent
import ru.boringowl.itroadmap.ui.features.profileedit.state.ProfileEditState
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel<ProfileEditState, ProfileEditEvent, ProfileEditEffect>() {
    override suspend fun handleEvent(event: ProfileEditEvent) {
        when (event) {
            ProfileEditEvent.Init -> createInitialState()
            ProfileEditEvent.GetCode -> getCode()
            ProfileEditEvent.SendCode -> sendCode()
            is ProfileEditEvent.SetCode -> setState { copy(verificationForm = verificationForm.copy(code = event.code)) }
            is ProfileEditEvent.SetPassword -> setState { copy(passwordForm = event.passwordForm) }
            is ProfileEditEvent.SetUser -> setState { copy(user = event.user) }
            ProfileEditEvent.UpdatePassword -> updatePassword()
            ProfileEditEvent.UpdateUser -> updateUser()
        }
    }

    private fun getCode() = launchIO {
        userRepository.verify()
            .onSuccess {
                setState { copy(loading = false, error = null) }
                updateTime()
            }
            .onLoading { setState { copy(loading = true, error = null) } }
            .onError { setState { copy(loading = false, error = it) } }
    }
    private fun sendCode() = launchIO {
        userRepository.activate(currentState.verificationForm.code)
            .onSuccess { setState { copy(loading = false, error = null, user = currentState.user?.copy(enabled = true)) } }
            .onLoading { setState { copy(loading = true, error = null) } }
            .onError { setState { copy(loading = false, error = it) } }
    }

    private fun updatePassword() = launchIO {
        val request = currentState.passwordForm.let {
            UpdatePasswordRequest(it.newPassword, it.matchingPassword, it.currentPassword)
        }
        userRepository.updatePassword(request)
            .onSuccess { setState { copy(success = true) } }
            .onLoading { setState { copy(loading = true, error = null) } }
            .onError { setState { copy(loading = false, error = it) } }
    }
    private fun updateUser() = launchIO {
        val request = currentState.user?.let {
            UpdateRequest(it.username, it.email, it.description, it.fullName)
        }!!
        userRepository.update(request).collectLatest {
            it
                .onSuccess { setState { copy(success = true) } }
                .onLoading { setState { copy(loading = true, error = null) } }
                .onError { setState { copy(loading = false, error = it) } }
        }
    }

    private fun updateTime() {
        setState {
            copy(
                verificationForm = currentState.verificationForm.copy(
                    codeRequested = System.currentTimeMillis()
                )
            )
        }
    }

    override fun createInitialState(): ProfileEditState {
        return ProfileEditState()
    }
}