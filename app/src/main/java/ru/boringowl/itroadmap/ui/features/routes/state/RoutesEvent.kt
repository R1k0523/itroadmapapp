package ru.boringowl.itroadmap.ui.features.routes.state

import ru.boringowl.itroadmap.ui.base.state.UiEvent


sealed class RoutesEvent : UiEvent {
    object Init : RoutesEvent()
    object Fetch : RoutesEvent()
    class SetSearchText(val query: String) : RoutesEvent()
    class SetTodoName(val name: String) : RoutesEvent()
    class SetSearchOpened(val opened: Boolean) : RoutesEvent()
    class SetDialogOpened(val opened: Boolean, val routeId: Int = -1) : RoutesEvent()
    object AddTodo : RoutesEvent()
    object SetDialogDefaults : RoutesEvent()
}
