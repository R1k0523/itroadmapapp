package ru.boringowl.itroadmap.data.persistence.room.competence.datasource

import ru.boringowl.itroadmap.data.persistence.room.competence.dao.SkillKeyDao
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.SkillRemoteKeys
import java.util.UUID
import javax.inject.Inject

class SkillKeyLocalDataSource @Inject constructor(private val dao: SkillKeyDao) {
    suspend fun insert(item: SkillRemoteKeys) = dao.insert(item)
    fun insert(items: List<SkillRemoteKeys>) = dao.insert(items)
    suspend fun update(item: SkillRemoteKeys) = dao.update(item)
    suspend fun delete(item: SkillRemoteKeys) = dao.delete(item)
    suspend fun delete() = dao.delete()
    suspend fun get(id: UUID): SkillRemoteKeys? = dao.get(id)

}