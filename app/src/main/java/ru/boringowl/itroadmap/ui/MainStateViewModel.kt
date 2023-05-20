package ru.boringowl.itroadmap.ui

import androidx.compose.runtime.Composable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.boringowl.itroadmap.domain.model.onSuccess
import ru.boringowl.itroadmap.domain.model.theme.UITheme
import ru.boringowl.itroadmap.domain.usecase.auth.IsSignedInUseCase
import ru.boringowl.itroadmap.domain.usecase.theme.GetThemeUseCase
import ru.boringowl.itroadmap.domain.usecase.theme.SetThemeUseCase
import ru.boringowl.itroadmap.ui.base.state.UiEffect
import ru.boringowl.itroadmap.ui.base.state.UiEvent
import ru.boringowl.itroadmap.ui.base.state.UiState
import ru.boringowl.itroadmap.ui.base.viewmodel.BaseViewModel
import ru.boringowl.itroadmap.ui.extensions.UIError
import javax.inject.Inject

@HiltViewModel
class MainStateViewModel @Inject constructor(
    val isSignedInUseCase: IsSignedInUseCase,
    val getThemeUseCase: GetThemeUseCase,
    val setThemeUseCase: SetThemeUseCase,
): BaseViewModel<MainState, MainEvent, MainEffect>() {

    override suspend fun handleEvent(event: MainEvent) {
        when(event) {
            MainEvent.DisableSplash -> setState { copy(splashEnabled = false) }
            MainEvent.FetchData -> fetchData()
            MainEvent.SetTheme -> setTheme()
        }
    }
    private fun fetchData() {
        launchIO {
            isSignedInUseCase().collectLatest {
                setState { copy(signedIn = it) }
            }
        }
        launchIO {
            getThemeUseCase().distinctUntilChanged().collectLatest {
                setState { copy(theme = it) }
            }
        }
    }
    private fun setTheme() {
        launchIO {
            setThemeUseCase(
                if (currentState.theme == UITheme.DARK) UITheme.LIGHT else UITheme.DARK
            ).onSuccess {
                fetchData()
            }
        }
    }
    override fun createInitialState(): MainState {
        return MainState()
    }
}


sealed class MainEffect : UiEffect

sealed class MainEvent : UiEvent {
    object DisableSplash : MainEvent()
    object FetchData : MainEvent()
    object SetTheme : MainEvent()
}

data class MainState(
    val theme: UITheme = UITheme.SYSTEM,
    val signedIn: Boolean = false,
    val splashEnabled: Boolean = true,
    override val error: UIError? = null,
    override val loading: Boolean = false,
) : UiState {
    @Composable
    fun isDarkTheme() = when (theme) {
        UITheme.LIGHT -> false
        else -> true
    }
}
