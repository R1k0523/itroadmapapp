package ru.boringowl.itroadmap.ui.base.state

import ru.boringowl.itroadmap.ui.extensions.UIError


interface UiEffect
interface UiEvent
interface UiState {
    val error: UIError?
    val loading: Boolean
}
