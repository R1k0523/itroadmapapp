package ru.boringowl.itroadmap.ui.features.todos.details.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.boringowl.itroadmap.domain.model.competence.SkillTodo
import ru.boringowl.itroadmap.domain.model.onError
import ru.boringowl.itroadmap.domain.model.onLoading
import ru.boringowl.itroadmap.domain.model.onSuccess
import ru.boringowl.itroadmap.domain.usecase.skilltodo.GetTodoSkillsUseCase
import ru.boringowl.itroadmap.domain.usecase.skilltodo.UpdateSkillTodoUseCase
import ru.boringowl.itroadmap.ui.base.viewmodel.BaseViewModel
import ru.boringowl.itroadmap.ui.features.todos.details.state.SortTodosBy
import ru.boringowl.itroadmap.ui.features.todos.details.state.TodoEffect
import ru.boringowl.itroadmap.ui.features.todos.details.state.TodoEvent
import ru.boringowl.itroadmap.ui.features.todos.details.state.TodoState
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    val getTodoSkillsUseCase: GetTodoSkillsUseCase,
    val updateSkillTodoUseCase: UpdateSkillTodoUseCase,
) : BaseViewModel<TodoState, TodoEvent, TodoEffect>() {
    override suspend fun handleEvent(event: TodoEvent) {
        when (event) {
            TodoEvent.Init -> createInitialState()
            is TodoEvent.SetSearchOpened -> {
                setState { copy(isSearchOpened = event.opened) }
                fetch()
            }
            is TodoEvent.SetSearchText -> {
                setState { copy(searchText = event.query) }
                fetch()
            }
            is TodoEvent.Fetch -> {
                setState { copy(todoId = event.todoId) }
                fetch()
            }
            is TodoEvent.UpdateSkillTodo -> updateSkillTodo(event.skillTodo)
            is TodoEvent.SetSortBy -> sort(event.sortBy)
        }
    }
    private suspend fun updateSkillTodo(skillTodo: SkillTodo) = launchIO {
        updateSkillTodoUseCase(skillTodo)
            .onSuccess { setState { copy(loading = false, error = null) } }
            .onLoading { setState { copy(loading = true, error = null) } }
            .onError { setState { copy(loading = false, error = it) } }
    }


    private fun sort(sortTodosBy: SortTodosBy) {
        setState { copy(sortType = sortTodosBy) }
        val sorted = when (currentState.sortType) {
            SortTodosBy.NAME -> currentState.skills.sortedBy { it.skillName }
            SortTodosBy.NECESSITY -> currentState.skills.sortedBy { it.necessity }.reversed()
            SortTodosBy.PROGRESS -> currentState.skills.sortedBy { it.progress }.reversed()
        }
        setState { copy(skills = sorted) }
    }

    private suspend fun fetch() = launchIO {
        val todoId = currentState.todoId ?: return@launchIO
        setState { copy(loading = true, error = null) }
        getTodoSkillsUseCase(todoId).collect {
            it.onSuccess { skills -> setState { copy(loading = false, error = null, skills = skills) } }
              .onLoading { setState { copy(loading = true, error = null) } }
              .onError { setState { copy(loading = false, error = it) } }
        }
    }

    override fun createInitialState(): TodoState = TodoState()
}