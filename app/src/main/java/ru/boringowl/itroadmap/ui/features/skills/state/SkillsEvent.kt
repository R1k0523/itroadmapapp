package ru.boringowl.itroadmap.ui.features.skills.state

import ru.boringowl.itroadmap.ui.base.state.UiEvent


sealed class SkillsEvent : UiEvent {
    object Init : SkillsEvent()
    class Fetch(val routeId: Int) : SkillsEvent()
    class UpdateSearch(val query: String) : SkillsEvent()
    class SetSearchOpened(val opened: Boolean) : SkillsEvent()
}
