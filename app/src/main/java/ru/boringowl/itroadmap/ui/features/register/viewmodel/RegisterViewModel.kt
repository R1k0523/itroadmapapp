package ru.boringowl.itroadmap.ui.features.register.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.boringowl.itroadmap.data.network.api.auth.request.SignUpRequest
import ru.boringowl.itroadmap.domain.model.onError
import ru.boringowl.itroadmap.domain.model.onLoading
import ru.boringowl.itroadmap.domain.model.onSuccess
import ru.boringowl.itroadmap.domain.usecase.auth.SignUpUseCase
import ru.boringowl.itroadmap.ui.base.viewmodel.BaseViewModel
import ru.boringowl.itroadmap.ui.features.register.state.RegisterEffect
import ru.boringowl.itroadmap.ui.features.register.state.RegisterEvent
import ru.boringowl.itroadmap.ui.features.register.state.RegisterState
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    val signUpUseCase: SignUpUseCase
) : BaseViewModel<RegisterState, RegisterEvent, RegisterEffect>() {
    override suspend fun handleEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.Init -> createInitialState()
            RegisterEvent.Register -> register()
            is RegisterEvent.SetForm -> setState { copy(form = event.form) }
            is RegisterEvent.SetPasswordVisibility -> setState { copy(passwordVisible = event.visible) }
            is RegisterEvent.SetPasswordRepeatVisibility -> setState { copy(passwordRepeatVisible = event.visible) }
        }
    }

    private suspend fun register() = launchIO {
        setState { copy(loading = true, error = null) }
        val request = currentState.form.run {
            SignUpRequest(username, email, password, passwordRepeat)
        }
        signUpUseCase(request)
            .onSuccess { setState { copy(loading = false, error = null, success = true) } }
            .onLoading { setState { copy(loading = true, error = null) } }
            .onError { setState { copy(loading = false, error = it) } }
    }

    override fun createInitialState(): RegisterState = RegisterState()
}