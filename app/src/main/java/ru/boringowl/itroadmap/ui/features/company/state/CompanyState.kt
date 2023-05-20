package ru.boringowl.itroadmap.ui.features.company.state

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.boringowl.itroadmap.domain.model.company.Company
import ru.boringowl.itroadmap.ui.base.state.UiState
import ru.boringowl.itroadmap.ui.extensions.UIError


data class CompanyState(
    val companys: Flow<PagingData<Company>> = flow { emit(PagingData.empty()) },
    val searchText: String = "",
    val isSearchOpened: Boolean = false,
    override val error: UIError? = null,
    override val loading: Boolean = false,
) : UiState

