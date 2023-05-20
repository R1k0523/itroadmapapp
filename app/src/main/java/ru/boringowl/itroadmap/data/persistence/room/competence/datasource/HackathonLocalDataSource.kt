package ru.boringowl.itroadmap.data.persistence.room.competence.datasource

import androidx.paging.PagingSource
import ru.boringowl.itroadmap.data.persistence.base.MappingPagingSource
import ru.boringowl.itroadmap.data.persistence.room.competence.dao.HackathonDao
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.HackathonEntity
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.toEntity
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.toModel
import ru.boringowl.itroadmap.domain.model.competence.Hackathon
import java.util.UUID
import javax.inject.Inject

class HackathonLocalDataSource @Inject constructor(private val dao: HackathonDao) {
    suspend fun insert(item: Hackathon) = dao.insert(item.toEntity())
    fun insert(items: List<Hackathon>) = dao.insert(items.map(Hackathon::toEntity))
    suspend fun update(item: Hackathon) = dao. update(item.toEntity())
    suspend fun delete(item: Hackathon) = dao.delete(item.toEntity())
    suspend fun delete(id: UUID) = dao.delete(id)
    fun get(): PagingSource<Int, Hackathon> = MappingPagingSource(dao.get(), HackathonEntity::toModel)
    suspend fun delete() = dao.delete()
    fun isExist(id : UUID) : Boolean = dao.isExist(id)
    suspend fun get(id: UUID): Hackathon? = dao.get(id)?.toModel()
    suspend fun clearAndInsert(items: List<Hackathon>) = dao.clearAndInsert(items.map(Hackathon::toEntity))
}