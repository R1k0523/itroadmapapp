package ru.boringowl.itroadmap.ui.features.company.state

import ru.boringowl.itroadmap.ui.base.state.UiEvent


sealed class CompanyEvent : UiEvent {
    object Init : CompanyEvent()
    object Fetch : CompanyEvent()
    class UpdateSearch(val query: String) : CompanyEvent()
    class SetSearchOpened(val opened: Boolean) : CompanyEvent()
}
