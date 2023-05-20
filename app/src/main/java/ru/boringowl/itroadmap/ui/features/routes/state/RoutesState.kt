package ru.boringowl.itroadmap.ui.features.routes.state

import ru.boringowl.itroadmap.domain.model.competence.Route
import ru.boringowl.itroadmap.ui.base.state.UiState
import ru.boringowl.itroadmap.ui.extensions.UIError


data class RoutesState(
    override val error: UIError? = null,
    val routes: List<Route> = listOf(),
    val searchText: String = "",
    val isSearchOpened: Boolean = false,
    val todoName: String = "",
    val routeId: Int = -1,
    val isDialogOpened: Boolean = false,
    override val loading: Boolean = false,
) : UiState

