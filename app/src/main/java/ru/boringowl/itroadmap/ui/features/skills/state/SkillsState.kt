package ru.boringowl.itroadmap.ui.features.skills.state

import ru.boringowl.itroadmap.domain.model.competence.Skill
import ru.boringowl.itroadmap.ui.base.state.UiState
import ru.boringowl.itroadmap.ui.extensions.UIError


data class SkillsState(
    val skills: List<Skill> = listOf(),
    val routeId: Int = 0,
    val searchText: String = "",
    val isSearchOpened: Boolean = false,
    override val error: UIError? = null,
    override val loading: Boolean = false,
) : UiState

