package ru.boringowl.itroadmap.ui.features.hackathon.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.boringowl.itroadmap.domain.usecase.hackathon.GetHackathonsUseCase
import ru.boringowl.itroadmap.ui.base.viewmodel.BaseViewModel
import ru.boringowl.itroadmap.ui.features.hackathon.state.HackathonsEffect
import ru.boringowl.itroadmap.ui.features.hackathon.state.HackathonsEvent
import ru.boringowl.itroadmap.ui.features.hackathon.state.HackathonsState
import javax.inject.Inject

@HiltViewModel
class HackathonViewModel @Inject constructor(
    val getHackathonsUseCase: GetHackathonsUseCase,
) : BaseViewModel<HackathonsState, HackathonsEvent, HackathonsEffect>() {
    var job: Job? = null
    override suspend fun handleEvent(event: HackathonsEvent) {
        when (event) {
            HackathonsEvent.Init -> createInitialState()
            HackathonsEvent.Fetch -> fetch()
            is HackathonsEvent.SetSearchOpened -> {
                setState { copy(isSearchOpened = event.opened) }
                fetch()
            }
            is HackathonsEvent.UpdateSearch -> {
                setState { copy(searchText = event.query) }
                fetch()
            }
        }
    }

    suspend fun fetch() = launchIO {
        setState { copy(loading = true) }
        if (job != null) job?.cancel()
        job = launchIO {
            val dataFlow = getHackathonsUseCase(currentState.searchText).distinctUntilChanged().cachedIn(viewModelScope)
            setState { copy(loading = false, hackathons = dataFlow) }
        }
    }
    override fun createInitialState(): HackathonsState = HackathonsState()
}