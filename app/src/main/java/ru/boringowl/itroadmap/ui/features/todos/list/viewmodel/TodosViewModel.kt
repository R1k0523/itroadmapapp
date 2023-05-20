package ru.boringowl.itroadmap.ui.features.todos.list.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import ru.boringowl.itroadmap.domain.model.onError
import ru.boringowl.itroadmap.domain.model.onLoading
import ru.boringowl.itroadmap.domain.model.onSuccess
import ru.boringowl.itroadmap.domain.usecase.todo.DeleteTodoUseCase
import ru.boringowl.itroadmap.domain.usecase.todo.GetTodosUseCase
import ru.boringowl.itroadmap.ui.base.viewmodel.BaseViewModel
import ru.boringowl.itroadmap.ui.features.todos.list.state.TodosEffect
import ru.boringowl.itroadmap.ui.features.todos.list.state.TodosEvent
import ru.boringowl.itroadmap.ui.features.todos.list.state.TodosState
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TodosViewModel @Inject constructor(
    val getTodosUseCase: GetTodosUseCase,
    val deleteTodoUseCase: DeleteTodoUseCase,
) : BaseViewModel<TodosState, TodosEvent, TodosEffect>() {
    override suspend fun handleEvent(event: TodosEvent) {
        when (event) {
            TodosEvent.Init -> createInitialState()
            TodosEvent.Fetch -> fetch()
            is TodosEvent.SetSearchOpened -> {
                setState { copy(isSearchOpened = event.opened) }
                fetch()
            }
            is TodosEvent.SetSearchText -> {
                setState { copy(searchText = event.query) }
                fetch()
            }

            is TodosEvent.DeleteTodo -> deleteTodo(event.id)
        }
    }

    private suspend fun deleteTodo(id: UUID) {
        deleteTodoUseCase(id)
            .onSuccess { setState { copy(loading = false, error = null) } }
            .onLoading { setState { copy(loading = true, error = null) } }
            .onError { setState { copy(loading = false, error = it) } }
    }

    private suspend fun fetch() = launchIO {
        setState { copy(loading = true, error = null) }
        getTodosUseCase().collectLatest {
            it.onSuccess { todos -> setState { copy(loading = false, error = null, todos = todos) } }
              .onLoading { setState { copy(loading = true, error = null) } }
              .onError { setState { copy(loading = false, error = it) } }
        }
    }

    override fun createInitialState(): TodosState = TodosState()
}