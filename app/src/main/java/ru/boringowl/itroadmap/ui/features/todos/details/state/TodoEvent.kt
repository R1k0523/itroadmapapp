package ru.boringowl.itroadmap.ui.features.todos.details.state

import ru.boringowl.itroadmap.domain.model.competence.SkillTodo
import ru.boringowl.itroadmap.ui.base.state.UiEvent
import java.util.UUID


sealed class TodoEvent : UiEvent {
    object Init : TodoEvent()
    class Fetch(val todoId: UUID) : TodoEvent()
    class SetSearchText(val query: String) : TodoEvent()
    class SetSearchOpened(val opened: Boolean) : TodoEvent()
    class UpdateSkillTodo(val skillTodo: SkillTodo) : TodoEvent()
    class SetSortBy(val sortBy: SortTodosBy) : TodoEvent()
}
