package ru.boringowl.itroadmap.ui.base.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.boringowl.itroadmap.ui.base.state.UiEffect
import ru.boringowl.itroadmap.ui.base.state.UiEvent
import ru.boringowl.itroadmap.ui.base.state.UiState

abstract class BaseViewModel<State : UiState, Event : UiEvent, Effect : UiEffect> : ViewModel() {
    val currentState: State
        get() = uiState.value

    private val initialState: State by lazy { createInitialState() }
    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        viewModelScope.launch {
            event.collect { handleEvent(it) }
        }
    }

    abstract suspend fun handleEvent(event: Event)

    abstract fun createInitialState(): State

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun setEffect(effect: Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }

    protected fun log(msg: String) {
        Log.e("ROADMAP_APP", msg)
    }

    fun launchIO(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            block()
        }
}