package ru.boringowl.itroadmap.data.persistence.room.competence.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.boringowl.itroadmap.data.persistence.room.competence.dao.TodoDao
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.TodoEntity
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.TodoWithSkills
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.toEntity
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.toModel
import ru.boringowl.itroadmap.domain.model.competence.Todo
import java.util.UUID
import javax.inject.Inject

class TodoLocalDataSource @Inject constructor(private val dao: TodoDao) {
    suspend fun insert(item:Todo) = dao.insert(item.toEntity())
    fun insert(items: List<Todo>) = dao.insert(items.map(Todo::toEntity))
    suspend fun update(item: Todo) = dao.update(item.toEntity())
    suspend fun delete(item:Todo) = dao.delete(item.toEntity())
    suspend fun delete(id: UUID) = dao.delete(id)
    fun get(): Flow<List<Todo>> = dao.get().map { list -> list.map(TodoWithSkills::toTodoModel) }
    fun getList(): List<Todo> = dao.getList().map(TodoEntity::toModel)
    suspend fun delete() = dao.delete()
    fun isExist(id: UUID): Boolean = dao.isExist(id)
    fun get(id: UUID): Flow<Todo?> = dao.get(id).map { it?.toTodoModel() }
    fun getItem(id: UUID): Todo = dao.getItem(id).toModel()
    suspend fun getNotUploaded(): List<Todo?> = dao.getNotUploaded().map(TodoEntity::toModel)
    suspend fun deleteById(id: UUID) = dao.deleteById(id)
    suspend fun refresh(items: List<Todo>) = dao.insertAndDelete(items.map(Todo::toEntity))
}