package ru.boringowl.itroadmap.ui.features.book.state

import ru.boringowl.itroadmap.ui.base.state.UiEvent


sealed class BooksEvent : UiEvent {
    object Init : BooksEvent()
    class Fetch(val routeId: Int) : BooksEvent()
    class UpdateSearch(val query: String) : BooksEvent()
    class SetSearchOpened(val opened: Boolean) : BooksEvent()
}
