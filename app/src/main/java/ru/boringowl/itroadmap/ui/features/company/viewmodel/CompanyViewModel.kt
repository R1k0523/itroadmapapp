package ru.boringowl.itroadmap.ui.features.company.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.boringowl.itroadmap.domain.usecase.company.GetCompaniesUseCase
import ru.boringowl.itroadmap.ui.base.viewmodel.BaseViewModel
import ru.boringowl.itroadmap.ui.features.company.state.CompanyEffect
import ru.boringowl.itroadmap.ui.features.company.state.CompanyEvent
import ru.boringowl.itroadmap.ui.features.company.state.CompanyState
import javax.inject.Inject

@HiltViewModel
class CompanyViewModel @Inject constructor(
    val getCompaniesUseCase: GetCompaniesUseCase,
) : BaseViewModel<CompanyState, CompanyEvent, CompanyEffect>() {
    var job: Job? = null
    override suspend fun handleEvent(event: CompanyEvent) {
        when (event) {
            CompanyEvent.Init -> createInitialState()
            CompanyEvent.Fetch -> fetch()
            is CompanyEvent.SetSearchOpened -> {
                setState { copy(isSearchOpened = event.opened) }
                fetch()
            }
            is CompanyEvent.UpdateSearch -> {
                setState { copy(searchText = event.query) }
                fetch()
            }
        }
    }

    suspend fun fetch() = launchIO {
        setState { copy(loading = true) }
        if (job != null) job?.cancel()
        job = launchIO {
            val dataFlow = getCompaniesUseCase(currentState.searchText).distinctUntilChanged().cachedIn(viewModelScope)
            setState { copy(loading = false, companys = dataFlow) }
        }
    }
    override fun createInitialState(): CompanyState = CompanyState()
}