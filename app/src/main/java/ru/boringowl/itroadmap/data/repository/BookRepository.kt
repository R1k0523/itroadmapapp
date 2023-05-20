package ru.boringowl.itroadmap.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.data.mediator.BookPagingSource
import ru.boringowl.itroadmap.data.network.api.route.datasource.BookRemoteDataSource
import ru.boringowl.itroadmap.data.persistence.room.competence.datasource.BookPostLocalDataSource
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.model.competence.BookPost
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val remoteDataSource: BookRemoteDataSource,
    private val localDataSource: BookPostLocalDataSource,
) {

    suspend fun add(model: BookPost): BaseResult<Unit> = try {
        BaseResult.Success(localDataSource.insert(model))
    } catch (e: Exception) {
        BaseResult.Error("Ошибка при вставке")
    }

    suspend fun update(model: BookPost) = try {
        BaseResult.Success(localDataSource.update(model))
    } catch (e: Exception) {
        BaseResult.Error("Ошибка при обновлении")
    }
    suspend fun delete(model: BookPost) = try {
        BaseResult.Success(localDataSource.delete(model))
    } catch (e: Exception) {
        BaseResult.Error("Ошибка при удалении")
    }

    suspend fun delete() = localDataSource.delete()


    fun get(query: String, routeId: Int): Flow<PagingData<BookPost>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { BookPagingSource(remoteDataSource, query, routeId) }
        ).flow
    }
}
