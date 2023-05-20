package ru.boringowl.itroadmap.data.network.api.route.datasource

import ru.boringowl.itroadmap.data.network.api.route.client.TodoApi
import ru.boringowl.itroadmap.data.network.api.route.response.TodoDto
import ru.boringowl.itroadmap.data.network.api.route.response.toDto
import ru.boringowl.itroadmap.data.network.api.route.response.toModel
import ru.boringowl.itroadmap.data.network.base.ResponseHandler
import ru.boringowl.itroadmap.domain.model.competence.Todo
import java.util.UUID
import javax.inject.Inject


class TodoRemoteDataSource @Inject constructor(
    private val api: TodoApi,
    private val handler: ResponseHandler,
) {
    suspend fun get(): List<Todo> = handler.process { api.get() }.items.map(TodoDto::toModel)
    suspend fun get(id: UUID): Todo = handler.process { api.get(id) }.toModel()
    suspend fun add(id: Int, name: String): Todo = handler.process { api.add(id, name) }.toModel()
    suspend fun update(model: Todo): Todo = handler.process { api.update(model.toDto()) }.toModel()
    suspend fun delete(id: UUID) = handler.process { api.delete(id) }


}