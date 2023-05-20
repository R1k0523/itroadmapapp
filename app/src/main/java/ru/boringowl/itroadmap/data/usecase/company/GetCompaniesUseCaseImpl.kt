package ru.boringowl.itroadmap.data.usecase.company

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.data.repository.CompanyRepository
import ru.boringowl.itroadmap.domain.model.company.Company
import ru.boringowl.itroadmap.domain.usecase.company.GetCompaniesUseCase
import javax.inject.Inject

class GetCompaniesUseCaseImpl @Inject constructor(
    private val repo: CompanyRepository,
): GetCompaniesUseCase {
    override suspend operator fun invoke(query: String): Flow<PagingData<Company>> = repo.get(query)
}