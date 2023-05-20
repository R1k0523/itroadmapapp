package ru.boringowl.itroadmap.ui.features.book.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.boringowl.itroadmap.domain.usecase.book.GetBooksUseCase
import ru.boringowl.itroadmap.ui.base.viewmodel.BaseViewModel
import ru.boringowl.itroadmap.ui.features.book.state.BooksEffect
import ru.boringowl.itroadmap.ui.features.book.state.BooksEvent
import ru.boringowl.itroadmap.ui.features.book.state.BooksState
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    val getBooksUseCase: GetBooksUseCase,
) : BaseViewModel<BooksState, BooksEvent, BooksEffect>() {
    var job: Job? = null
    override suspend fun handleEvent(event: BooksEvent) {
        when (event) {
            BooksEvent.Init -> createInitialState()
            is BooksEvent.Fetch -> {
                setState { copy(routeId = event.routeId) }
                fetch()
            }
            is BooksEvent.SetSearchOpened -> {
                setState { copy(isSearchOpened = event.opened) }
                fetch()
            }
            is BooksEvent.UpdateSearch -> {
                setState { copy(searchText = event.query) }
                fetch()
            }
        }
    }

    private suspend fun fetch() = launchIO {
        setState { copy(loading = true) }
        if (job != null) job?.cancel()
        job = launchIO {

            val dataFlow = getBooksUseCase(
                currentState.searchText,
                currentState.routeId)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
            setState { copy(loading = false, books = dataFlow) }
        }
    }
    override fun createInitialState(): BooksState = BooksState()
}