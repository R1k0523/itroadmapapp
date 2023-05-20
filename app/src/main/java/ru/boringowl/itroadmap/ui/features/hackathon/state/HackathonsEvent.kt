package ru.boringowl.itroadmap.ui.features.hackathon.state

import ru.boringowl.itroadmap.ui.base.state.UiEvent


sealed class HackathonsEvent : UiEvent {
    object Init : HackathonsEvent()
    object Fetch : HackathonsEvent()
    class UpdateSearch(val query: String) : HackathonsEvent()
    class SetSearchOpened(val opened: Boolean) : HackathonsEvent()
}
