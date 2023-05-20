package ru.boringowl.itroadmap.ui.features.restore.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.boringowl.itroadmap.domain.model.onError
import ru.boringowl.itroadmap.domain.model.onLoading
import ru.boringowl.itroadmap.domain.model.onSuccess
import ru.boringowl.itroadmap.domain.usecase.auth.GetTokenForResetUseCase
import ru.boringowl.itroadmap.domain.usecase.auth.RequestResetUseCase
import ru.boringowl.itroadmap.domain.usecase.auth.ResetPasswordUseCase
import ru.boringowl.itroadmap.ui.base.viewmodel.BaseViewModel
import ru.boringowl.itroadmap.ui.extensions.UIError
import ru.boringowl.itroadmap.ui.features.restore.state.RestoreEffect
import ru.boringowl.itroadmap.ui.features.restore.state.RestoreEvent
import ru.boringowl.itroadmap.ui.features.restore.state.RestoreState
import ru.boringowl.itroadmap.ui.features.restore.state.RestoreStep
import javax.inject.Inject

@HiltViewModel
class RestoreViewModel @Inject constructor(
    val resetPasswordUseCase: ResetPasswordUseCase,
    val getTokenForResetUseCase: GetTokenForResetUseCase,
    val requestResetUseCase: RequestResetUseCase,
) : BaseViewModel<RestoreState, RestoreEvent, RestoreEffect>() {
    override suspend fun handleEvent(event: RestoreEvent) {
        when (event) {
            RestoreEvent.Init -> createInitialState()
            is RestoreEvent.SetForm -> setState { copy(form = event.form) }
            RestoreEvent.GetCode -> getCode()
            RestoreEvent.GetToken -> getToken()
            RestoreEvent.SetPassword -> setPassword()
            is RestoreEvent.SetPasswordRepeatVisibility -> setState { copy(passwordRepeatVisible = event.visible) }
            is RestoreEvent.SetPasswordVisibility -> setState { copy(passwordVisible = event.visible) }
        }
    }

    private suspend fun getCode() = launchIO {
        loading()
        requestResetUseCase(currentState.form.email)
            .onSuccess {
                setState {
                    copy(
                        loading = false,
                        error = null,
                        restoreStep = RestoreStep.Code
                    )
                }
            }
            .onLoading { loading() }
            .onError { error(it) }
    }

    private suspend fun getToken() = launchIO {
        loading()
        getTokenForResetUseCase(currentState.form.email, currentState.form.code)
            .onSuccess {
                setState {
                    copy(
                        loading = false,
                        error = null,
                        restoreStep = RestoreStep.Password,
                        token = it
                    )
                }
            }
            .onLoading { loading() }
            .onError { error(it) }
    }

    private suspend fun setPassword() = launchIO {
        loading()
        currentState.run {
            resetPasswordUseCase(token, form.password, form.passwordRepeat)
                .onSuccess {
                    setState {
                        copy(loading = false,
                            error = null,
                            restoreStep = RestoreStep.Finish
                        )
                    }
                }
                .onLoading { loading() }
                .onError { error(it) }
        }
    }

    private fun loading() = setState { copy(loading = true, error = null) }
    private fun error(error: UIError?) = setState { copy(loading = false, error = error) }

    override fun createInitialState(): RestoreState = RestoreState()
}