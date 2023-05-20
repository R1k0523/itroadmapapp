package ru.boringowl.itroadmap.data.persistence.room.competence.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.boringowl.itroadmap.data.persistence.room.competence.dao.SkillDao
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.toEntity
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.toModel
import ru.boringowl.itroadmap.domain.model.competence.Skill
import java.util.UUID
import javax.inject.Inject

class SkillLocalDataSource @Inject constructor(private val dao: SkillDao) {
    suspend fun insert(item: Skill) = dao.insert(item.toEntity())
    fun insert(items: List<Skill>) = dao.insert(items.map(Skill::toEntity))
    suspend fun update(item: Skill) = dao.update(item.toEntity())
    suspend fun delete(item: Skill) = dao.delete(item.toEntity())
    suspend fun delete(id: UUID) = dao.delete(id)
    fun getByRoute(id: Int): Flow<List<Skill>> = dao.getByRoute(id).map { f -> f.map { it.skill.toModel() } }
    suspend fun delete() = dao.delete()
    fun isExist(id: Int): Boolean = dao.isExist(id)
    suspend fun get(id: UUID): Skill? = dao.get(id)?.toModel()
    suspend fun refresh(items: List<Skill>, routeId: Int) = dao.insertAndDelete(items.map {
        it.toEntity(routeId)
    }
    )
}