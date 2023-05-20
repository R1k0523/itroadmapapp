package ru.boringowl.itroadmap.data.persistence.room.competence.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.boringowl.itroadmap.data.persistence.room.competence.dao.SkillTodoDao
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.SkillTodoEntity
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.toEntity
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.toModel
import ru.boringowl.itroadmap.domain.model.competence.SkillTodo
import java.util.UUID
import javax.inject.Inject

class SkillTodoLocalDataSource @Inject constructor(private val dao: SkillTodoDao) {
    suspend fun insert(item:SkillTodo) = dao.insert(item.toEntity())
    fun insert(items: List<SkillTodo>) = dao.insert(items.map(SkillTodo::toEntity))
    suspend fun update(item: SkillTodo) {
        dao.get(item.skillTodoId)?.let {
            dao.update(item.toEntity(it.todoId))
        }

    }
    suspend fun delete(item:SkillTodo) = dao.delete(item.toEntity())
    suspend fun delete(id: UUID) = dao.delete(id)
    suspend fun delete() = dao.delete()
    suspend fun get(id: UUID):SkillTodo? = dao.get(id)?.toModel()
    fun isExist(id : UUID) : Boolean = dao.isExist(id)
    fun getByTodo(todo: UUID): Flow<List<SkillTodo>> = dao.getByTodo(todo).map { it.map(SkillTodoEntity::toModel) }
    suspend fun getNotUploaded(): List<SkillTodoEntity> = dao.getNotUploaded()
    suspend fun refresh(items: List<SkillTodo>) = dao.insertAndDelete(items.map(SkillTodo::toEntity))

}