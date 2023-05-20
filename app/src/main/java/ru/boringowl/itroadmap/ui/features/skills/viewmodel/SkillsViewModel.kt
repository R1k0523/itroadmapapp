package ru.boringowl.itroadmap.ui.features.skills.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import ru.boringowl.itroadmap.domain.model.onError
import ru.boringowl.itroadmap.domain.model.onLoading
import ru.boringowl.itroadmap.domain.model.onSuccess
import ru.boringowl.itroadmap.domain.usecase.skill.GetSkillsUseCase
import ru.boringowl.itroadmap.ui.base.viewmodel.BaseViewModel
import ru.boringowl.itroadmap.ui.features.skills.state.SkillsEffect
import ru.boringowl.itroadmap.ui.features.skills.state.SkillsEvent
import ru.boringowl.itroadmap.ui.features.skills.state.SkillsState
import javax.inject.Inject

@HiltViewModel
class SkillsViewModel @Inject constructor(
    val getSkillsUseCase: GetSkillsUseCase
) : BaseViewModel<SkillsState, SkillsEvent, SkillsEffect>() {
    override suspend fun handleEvent(event: SkillsEvent) {
        when (event) {
            SkillsEvent.Init -> createInitialState()
            is SkillsEvent.Fetch -> {
                setState { copy(routeId = event.routeId) }
                fetch()
            }
            is SkillsEvent.SetSearchOpened -> {
                setState { copy(isSearchOpened = event.opened) }
                fetch()
            }
            is SkillsEvent.UpdateSearch -> {
                setState { copy(searchText = event.query) }
                fetch()
            }
        }
    }

    private suspend fun fetch() = launchIO {
        setState { copy(loading = true, error = null) }
        getSkillsUseCase(currentState.routeId).collectLatest {
            it.onSuccess { skills -> setState { copy(loading = false, error = null, skills = skills) } }
              .onLoading { setState { copy(loading = true, error = null) } }
              .onError { setState { copy(loading = false, error = it) } }
        }
    }

    override fun createInitialState(): SkillsState = SkillsState()
}