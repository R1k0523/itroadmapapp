package ru.boringowl.itroadmap.ui.features.auth.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.boringowl.itroadmap.data.network.api.auth.request.SignInRequest
import ru.boringowl.itroadmap.domain.model.onError
import ru.boringowl.itroadmap.domain.model.onLoading
import ru.boringowl.itroadmap.domain.model.onSuccess
import ru.boringowl.itroadmap.domain.usecase.auth.SignInUseCase
import ru.boringowl.itroadmap.ui.base.viewmodel.BaseViewModel
import ru.boringowl.itroadmap.ui.features.auth.state.AuthEffect
import ru.boringowl.itroadmap.ui.features.auth.state.AuthEvent
import ru.boringowl.itroadmap.ui.features.auth.state.AuthState
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val signInUseCase: SignInUseCase,
) : BaseViewModel<AuthState, AuthEvent, AuthEffect>() {
    override fun createInitialState(): AuthState = AuthState()

    override suspend fun handleEvent(event: AuthEvent) {
        when (event) {
            AuthEvent.Init -> createInitialState()
            AuthEvent.Authenticate -> authenticate()
            is AuthEvent.SetPassword -> setState { copy(password = event.password) }
            is AuthEvent.SetPasswordVisibility -> setState { copy(passwordVisible = event.visible) }
            is AuthEvent.SetUsername -> setState { copy(username = event.username) }
        }
    }

    private suspend fun authenticate() = launchIO {
        setState { copy(loading = true, error = null) }
        signInUseCase(
            SignInRequest(
                currentState.username,
                currentState.password,
            )
        )
            .onSuccess { setState { copy(loading = false, error = null) } }
            .onLoading { setState { copy(loading = true, error = null) } }
            .onError { setState { copy(loading = false, error = it) } }
    }


}