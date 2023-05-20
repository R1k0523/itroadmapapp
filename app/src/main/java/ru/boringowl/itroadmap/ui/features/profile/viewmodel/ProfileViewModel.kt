package ru.boringowl.itroadmap.ui.features.profile.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.model.onError
import ru.boringowl.itroadmap.domain.model.onLoading
import ru.boringowl.itroadmap.domain.model.onSuccess
import ru.boringowl.itroadmap.domain.usecase.auth.LogoutUseCase
import ru.boringowl.itroadmap.domain.usecase.file.UploadProfileImageUseCase
import ru.boringowl.itroadmap.domain.usecase.user.GetMeUseCase
import ru.boringowl.itroadmap.ui.base.viewmodel.BaseViewModel
import ru.boringowl.itroadmap.ui.extensions.UIError
import ru.boringowl.itroadmap.ui.features.profile.state.ProfileEffect
import ru.boringowl.itroadmap.ui.features.profile.state.ProfileEvent
import ru.boringowl.itroadmap.ui.features.profile.state.ProfileState
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val getMeUseCase: GetMeUseCase,
    val logoutUseCase: LogoutUseCase,
    val uploadProfileImageUseCase: UploadProfileImageUseCase
) : BaseViewModel<ProfileState, ProfileEvent, ProfileEffect>() {
    override suspend fun handleEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.Init -> createInitialState()
            ProfileEvent.Logout -> logout()
            ProfileEvent.FetchMe -> fetchUser()
            is ProfileEvent.UploadProfileImage -> uploadImage(event.file)
        }
    }

    private suspend fun logout() {
        logoutUseCase()
    }

    private fun fetchUser() {
        launchIO {
            getMeUseCase().collectLatest {
                when (it) {
                    is BaseResult.Success -> setState { copy(loading = false, user = it.data) }
                    BaseResult.Loading -> setState { copy(loading = true) }
                    else -> logout()
                }
            }
        }
    }

    private fun uploadImage(file: File) = launchIO {
        uploadProfileImageUseCase(file).onSuccess {
            fetchUser()
        }.onError {
            setState { copy(error = UIError("Ошибка при загрузке")) }
        }.onLoading {
            setState { copy(loading = true) }
        }
    }
    override fun createInitialState(): ProfileState {
        return ProfileState()
    }
}