package ru.boringowl.itroadmap.data.repository

import ru.boringowl.itroadmap.data.network.api.route.datasource.SkillRemoteDataSource
import ru.boringowl.itroadmap.data.network.base.RequestHandler
import ru.boringowl.itroadmap.data.persistence.room.competence.datasource.SkillLocalDataSource
import ru.boringowl.itroadmap.domain.model.FlowResult
import ru.boringowl.itroadmap.domain.model.competence.Skill
import javax.inject.Inject

class SkillRepository @Inject constructor(
    private val remoteDataSource: SkillRemoteDataSource,
    private val localDataSource: SkillLocalDataSource,
    private val handler: RequestHandler
) {
    suspend fun getSkills(routeId: Int): FlowResult<List<Skill>> {
        return handler.execute(
            processRequest = { remoteDataSource.getByRoute(routeId) },
            onAfter = { localDataSource.getByRoute(routeId) },
            doSync = { todos -> localDataSource.refresh(todos, routeId)},
        )
    }
}
