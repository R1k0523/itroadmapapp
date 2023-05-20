package ru.boringowl.itroadmap.data.repository

import ru.boringowl.itroadmap.data.network.api.route.datasource.TodoRemoteDataSource
import ru.boringowl.itroadmap.data.network.base.RequestHandler
import ru.boringowl.itroadmap.data.persistence.room.competence.datasource.TodoLocalDataSource
import ru.boringowl.itroadmap.domain.model.FlowResult
import ru.boringowl.itroadmap.domain.model.competence.Todo
import java.util.UUID
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val localDataSource: TodoLocalDataSource,
    private val remoteDataSource: TodoRemoteDataSource,
    private val todoSkillRepository: TodoSkillRepository,
    private val handler: RequestHandler,
) {
    suspend fun add(
        routeId: Int,
        name: String,
    ) = handler.execute {
        val model = remoteDataSource.add(routeId, name)
        model.skills.forEach {
            it.todo = model
            todoSkillRepository.add(it)
        }
        localDataSource.insert(model)
    }

    suspend fun update(todo: Todo) = handler.execute {
        val model = remoteDataSource.update(todo)
        localDataSource.insert(model)
    }

    suspend fun delete(id: UUID) = handler.execute {
        remoteDataSource.delete(id)
        localDataSource.delete(id)
    }

    fun get() = handler.execute(
        processRequest = { remoteDataSource.get() },
        onAfter = { localDataSource.get() },
        doSync = { todos -> localDataSource.refresh(todos) },
    )
    suspend fun get(todoId: UUID): FlowResult<Todo> {
        return handler.execute(
            processRequest = { remoteDataSource.get(todoId) },
            onAfter = { todoSkillRepository.get(todoId) },
            doSync = { localDataSource.refresh(listOf(it)) },
        )
    }
}