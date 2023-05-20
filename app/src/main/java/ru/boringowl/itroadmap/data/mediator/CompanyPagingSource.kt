package ru.boringowl.itroadmap.data.mediator

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.boringowl.itroadmap.data.network.api.route.datasource.CompanyRemoteDataSource
import ru.boringowl.itroadmap.domain.model.company.Company

class CompanyPagingSource(
    private val remoteDataSource: CompanyRemoteDataSource,
    private val query: String
): PagingSource<Int, Company>() {
    override fun getRefreshKey(state: PagingState<Int, Company>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Company> {
        return try {
            val page = params.key ?: 0
            val response = remoteDataSource.get(page = page, query = query)

            LoadResult.Page(
                data = response.content,
                prevKey = if (response.first) null else page.minus(1),
                nextKey = if (response.last) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}