package ru.boringowl.itroadmap.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.boringowl.itroadmap.data.network.api.route.datasource.SkillTodoRemoteDataSource
import ru.boringowl.itroadmap.data.network.base.RequestHandler
import ru.boringowl.itroadmap.data.persistence.room.competence.datasource.SkillTodoLocalDataSource
import ru.boringowl.itroadmap.data.persistence.room.competence.datasource.TodoLocalDataSource
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.model.FlowResult
import ru.boringowl.itroadmap.domain.model.competence.SkillTodo
import ru.boringowl.itroadmap.domain.model.competence.Todo
import java.util.UUID
import javax.inject.Inject


class TodoSkillRepository @Inject constructor(
    private val localDataSource: SkillTodoLocalDataSource,
    private val todoLocalDataSource: TodoLocalDataSource,
    private val remoteDataSource: SkillTodoRemoteDataSource,
    private val handler: RequestHandler,
) {

    suspend fun add(model: SkillTodo) = localDataSource.insert(model)

    suspend fun update(item: SkillTodo): BaseResult<SkillTodo> = handler.execute {
        val saved = remoteDataSource.update(item)
        localDataSource.update(saved)
        localDataSource.get(saved.skillTodoId)!!
    }

    suspend fun delete(model: SkillTodo) = handler.execute {
        remoteDataSource.delete(model.skillTodoId)
        localDataSource.delete(model.skillTodoId)
    }

    suspend fun delete() = localDataSource.delete()

    fun get(todoId: UUID): Flow<Todo?> = todoLocalDataSource.get(todoId)
    fun getSkills(todoId: UUID): FlowResult<List<SkillTodo>> = localDataSource.getByTodo(todoId).map { BaseResult.Success(it) }
}