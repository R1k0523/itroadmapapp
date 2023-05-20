package ru.boringowl.itroadmap.data.persistence.room.competence.datasource

import androidx.paging.PagingSource
import ru.boringowl.itroadmap.data.persistence.base.MappingPagingSource
import ru.boringowl.itroadmap.data.persistence.room.competence.dao.BookPostDao
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.BookPostEntity
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.toEntity
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.toModel
import ru.boringowl.itroadmap.domain.model.competence.BookPost
import java.util.UUID
import javax.inject.Inject

class BookPostLocalDataSource @Inject constructor(private val dao: BookPostDao) {
    suspend fun insert(item: BookPost) = dao.insert(item.toEntity())
    fun insert(items: List<BookPost>) = dao.insert(items.map(BookPost::toEntity))
    suspend fun update(item: BookPost) = dao.update(item.toEntity())
    suspend fun delete(item: BookPost) = dao.delete(item.toEntity())
    suspend fun delete(id: UUID) = dao.delete(id)
    fun getByRoute(id: Int): PagingSource<Int, BookPost> = MappingPagingSource(dao.getByRoute(id), BookPostEntity::toModel)
    suspend fun delete() = dao.delete()
    fun isExist(id : UUID) : Boolean = dao.isExist(id)
    suspend fun get(id: UUID): BookPost? = dao.get(id)?.toModel()

}