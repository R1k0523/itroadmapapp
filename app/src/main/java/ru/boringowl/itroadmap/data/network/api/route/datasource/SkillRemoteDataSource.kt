package ru.boringowl.itroadmap.data.network.api.route.datasource

import ru.boringowl.itroadmap.data.network.api.route.client.SkillApi
import ru.boringowl.itroadmap.data.network.api.route.response.SkillResponse
import ru.boringowl.itroadmap.data.network.api.route.response.toModel
import ru.boringowl.itroadmap.data.network.base.ResponseHandler
import ru.boringowl.itroadmap.domain.model.competence.Skill
import java.util.UUID
import javax.inject.Inject


class SkillRemoteDataSource @Inject constructor(
    private val api: SkillApi,
    private val handler: ResponseHandler,
) {
    suspend fun getByRoute(routeId: Int): List<Skill> = handler.process { api.getByRoute(routeId) }.items.map(SkillResponse::toModel)
    suspend fun get(id: UUID): Skill = handler.process { api.get(id) }.toModel()
}