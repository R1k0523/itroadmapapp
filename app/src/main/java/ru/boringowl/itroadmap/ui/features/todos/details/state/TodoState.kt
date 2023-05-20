package ru.boringowl.itroadmap.ui.features.todos.details.state

import androidx.annotation.StringRes
import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.domain.model.competence.SkillTodo
import ru.boringowl.itroadmap.ui.base.state.UiState
import ru.boringowl.itroadmap.ui.extensions.UIError
import java.util.UUID


data class TodoState(
    val skills: List<SkillTodo> = listOf(),
    val searchText: String = "",
    val isSearchOpened: Boolean = false,
    val todoId: UUID? = null,
    val sortType: SortTodosBy = SortTodosBy.NECESSITY,
    val isDialogOpened: Boolean = false,
    override val error: UIError? = null,
    override val loading: Boolean = false,
) : UiState


enum class SortTodosBy(@StringRes val nameRes: Int) {
    NAME(R.string.by_name), NECESSITY(R.string.by_necessity), PROGRESS(R.string.by_progress)
}