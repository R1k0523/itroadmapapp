package ru.boringowl.itroadmap.ui.features.routes.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import ru.boringowl.itroadmap.domain.model.onError
import ru.boringowl.itroadmap.domain.model.onLoading
import ru.boringowl.itroadmap.domain.model.onSuccess
import ru.boringowl.itroadmap.domain.usecase.route.GetRoutesUseCase
import ru.boringowl.itroadmap.domain.usecase.todo.AddTodoUseCase
import ru.boringowl.itroadmap.ui.base.viewmodel.BaseViewModel
import ru.boringowl.itroadmap.ui.features.routes.state.RoutesEffect
import ru.boringowl.itroadmap.ui.features.routes.state.RoutesEvent
import ru.boringowl.itroadmap.ui.features.routes.state.RoutesState
import javax.inject.Inject

@HiltViewModel
class RoutesViewModel @Inject constructor(
    val getRoutesUseCase: GetRoutesUseCase,
    val addTodoUseCase: AddTodoUseCase,
) : BaseViewModel<RoutesState, RoutesEvent, RoutesEffect>() {
    override suspend fun handleEvent(event: RoutesEvent) {
        when (event) {
            RoutesEvent.Init -> createInitialState()
            RoutesEvent.Fetch -> fetch()
            is RoutesEvent.SetSearchOpened -> {
                setState { copy(isSearchOpened = event.opened) }
                fetch()
            }
            is RoutesEvent.SetSearchText -> {
                setState { copy(searchText = event.query) }
                fetch()
            }

            is RoutesEvent.AddTodo -> addTodo()
            RoutesEvent.SetDialogDefaults -> setState {
                copy(isDialogOpened = false, todoName = "")
            }
            is RoutesEvent.SetDialogOpened -> setState { copy(isDialogOpened = event.opened, routeId = event.routeId) }
            is RoutesEvent.SetTodoName -> setState { copy(todoName = event.name) }
        }
    }

    private suspend fun addTodo() {
        addTodoUseCase(currentState.routeId, currentState.todoName)
            .onSuccess {
                setState { copy(loading = false, error = null, isDialogOpened = false) }
            }
            .onLoading { setState { copy(loading = true, error = null) } }
            .onError { setState { copy(loading = false, error = it) } }
    }

    private suspend fun fetch() = launchIO {
        setState { copy(loading = true, error = null) }
        getRoutesUseCase().collectLatest {
            it.onSuccess { routes -> setState { copy(loading = false, error = null, routes = routes) } }
              .onLoading { setState { copy(loading = true, error = null) } }
              .onError { setState { copy(loading = false, error = it) } }
        }
    }

    override fun createInitialState(): RoutesState = RoutesState()
}