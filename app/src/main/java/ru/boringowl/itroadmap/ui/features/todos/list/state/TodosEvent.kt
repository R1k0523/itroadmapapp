package ru.boringowl.itroadmap.ui.features.todos.list.state

import ru.boringowl.itroadmap.ui.base.state.UiEvent
import java.util.UUID


sealed class TodosEvent : UiEvent {
    object Init : TodosEvent()
    object Fetch : TodosEvent()
    class SetSearchText(val query: String) : TodosEvent()
    class SetSearchOpened(val opened: Boolean) : TodosEvent()
    class DeleteTodo(val id: UUID) : TodosEvent()
}
