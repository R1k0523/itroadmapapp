package ru.boringowl.itroadmap.data.network.api.route.datasource

import ru.boringowl.itroadmap.data.network.api.route.client.SkillTodoApi
import ru.boringowl.itroadmap.data.network.api.route.response.SkillTodoDto
import ru.boringowl.itroadmap.data.network.api.route.response.toDto
import ru.boringowl.itroadmap.data.network.api.route.response.toModel
import ru.boringowl.itroadmap.data.network.base.ResponseHandler
import ru.boringowl.itroadmap.domain.model.competence.SkillTodo
import java.util.UUID
import javax.inject.Inject


class SkillTodoRemoteDataSource @Inject constructor(
    private val api: SkillTodoApi,
    private val handler: ResponseHandler,
) {
    suspend fun getByTodo(todoId: UUID): List<SkillTodo> = handler.process { api.getByTodo( todoId) }.items.map(SkillTodoDto::toModel)
    suspend fun get(skillTodoId: UUID): SkillTodo = handler.process { api.get( skillTodoId) }.toModel()
    suspend fun add(model: SkillTodo): SkillTodo = handler.process { api.add(model.toDto()) }.toModel()
    suspend fun update(model: SkillTodo): SkillTodo = handler.process { api.update(model.toDto()) }.toModel()
    suspend fun delete(id: UUID) = handler.process { api.delete(id) }
}