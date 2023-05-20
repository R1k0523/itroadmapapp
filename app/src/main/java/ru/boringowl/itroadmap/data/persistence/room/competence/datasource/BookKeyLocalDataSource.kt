package ru.boringowl.itroadmap.data.persistence.room.competence.datasource

import ru.boringowl.itroadmap.data.persistence.room.competence.dao.BookKeyDao
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.BookPostKeys
import java.util.UUID
import javax.inject.Inject

class BookKeyLocalDataSource @Inject constructor(private val dao: BookKeyDao) {
    suspend fun insert(item: BookPostKeys) = dao.insert(item)
    fun insert(items: List<BookPostKeys>) = dao.insert(items)
    suspend fun update(item: BookPostKeys) = dao.update(item)
    suspend fun delete(item: BookPostKeys) = dao.delete(item)
    suspend fun delete() = dao.delete()
    suspend fun get(id: UUID): BookPostKeys? = dao.get(id)
}