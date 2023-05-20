package ru.boringowl.itroadmap.domain.usecase.company

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.domain.model.company.Company

interface GetCompaniesUseCase {
    suspend operator fun invoke(query: String): Flow<PagingData<Company>>
}