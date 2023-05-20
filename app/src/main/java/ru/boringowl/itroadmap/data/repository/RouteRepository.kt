package ru.boringowl.itroadmap.data.repository

import ru.boringowl.itroadmap.data.network.api.route.datasource.RouteRemoteDataSource
import ru.boringowl.itroadmap.data.network.base.RequestHandler
import ru.boringowl.itroadmap.data.persistence.room.competence.datasource.RouteLocalDataSource
import ru.boringowl.itroadmap.domain.model.FlowResult
import ru.boringowl.itroadmap.domain.model.competence.Route
import javax.inject.Inject

class RouteRepository @Inject constructor(
    private val remoteDataSource: RouteRemoteDataSource,
    private val localDataSource: RouteLocalDataSource,
    private val handler: RequestHandler
) {
    suspend fun getRoutes(): FlowResult<List<Route>> {
        return handler.execute(
            processRequest = { remoteDataSource.get() },
            onAfter = { localDataSource.get() },
            doSync = { localDataSource.refresh(it) },
        )
    }
}
