package ru.boringowl.itroadmap.data.persistence.room.competence.datasource

import ru.boringowl.itroadmap.data.persistence.room.competence.dao.HackathonKeyDao
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.HackathonKeys
import java.util.UUID
import javax.inject.Inject

class HackathonKeyLocalDataSource @Inject constructor(private val dao: HackathonKeyDao) {
    suspend fun insert(item: HackathonKeys) = dao.insert(item)
    fun insert(items: List<HackathonKeys>) = dao.insert(items)
    suspend fun update(item: HackathonKeys) = dao.update(item)
    suspend fun delete(item: HackathonKeys) = dao.delete(item)
    suspend fun delete() = dao.delete()
    suspend fun get(id: UUID): HackathonKeys? = dao.get(id)


}