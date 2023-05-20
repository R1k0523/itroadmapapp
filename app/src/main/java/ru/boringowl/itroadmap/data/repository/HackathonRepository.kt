package ru.boringowl.itroadmap.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.data.mediator.HackathonPagingSource
import ru.boringowl.itroadmap.data.network.api.route.datasource.HackathonRemoteDataSource
import ru.boringowl.itroadmap.data.persistence.room.competence.datasource.HackathonLocalDataSource
import ru.boringowl.itroadmap.data.persistence.room.db.LocalDatabase
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.model.competence.Hackathon
import javax.inject.Inject

class HackathonRepository @Inject constructor(
    private val remoteDataSource: HackathonRemoteDataSource,
    private val localDataSource: HackathonLocalDataSource,
) {

    suspend fun add(model: Hackathon): BaseResult<Unit> = try {
        BaseResult.Success(localDataSource.insert(model))
    } catch (e: Exception) {
        BaseResult.Error("Ошибка при вставке")
    }

    suspend fun update(model: Hackathon) = try {
        BaseResult.Success(localDataSource.update(model))
    } catch (e: Exception) {
        BaseResult.Error("Ошибка при обновлении")
    }
    suspend fun delete(model: Hackathon) = try {
        BaseResult.Success(localDataSource.delete(model))
    } catch (e: Exception) {
        BaseResult.Error("Ошибка при удалении")
    }

    suspend fun delete() = localDataSource.delete()


    fun get(query: String): Flow<PagingData<Hackathon>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { HackathonPagingSource(remoteDataSource, query) }
        ).flow
    }
}
