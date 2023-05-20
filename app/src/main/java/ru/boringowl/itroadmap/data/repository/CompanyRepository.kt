package ru.boringowl.itroadmap.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.data.mediator.CompanyPagingSource
import ru.boringowl.itroadmap.data.network.api.route.datasource.CompanyRemoteDataSource
import ru.boringowl.itroadmap.domain.model.company.Company
import javax.inject.Inject

class CompanyRepository @Inject constructor(
    private val remoteDataSource: CompanyRemoteDataSource,
) {
    fun get(query: String): Flow<PagingData<Company>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { CompanyPagingSource(remoteDataSource, query) }
        ).flow
    }
}
