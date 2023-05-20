package ru.boringowl.itroadmap.ui.features.todos.list.state

import ru.boringowl.itroadmap.domain.model.competence.Todo
import ru.boringowl.itroadmap.ui.base.state.UiState
import ru.boringowl.itroadmap.ui.extensions.UIError


data class TodosState(
    val todos: List<Todo> = listOf(),
    val searchText: String = "",
    val isSearchOpened: Boolean = false,
    val todoName: String = "",
    val todoId: Int = -1,
    override val error: UIError? = null,
    override val loading: Boolean = false,
) : UiState

