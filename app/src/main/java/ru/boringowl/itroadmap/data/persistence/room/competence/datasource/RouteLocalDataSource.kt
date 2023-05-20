package ru.boringowl.itroadmap.data.persistence.room.competence.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.boringowl.itroadmap.data.persistence.room.competence.dao.RouteDao
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.RouteEntity
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.RouteWithSkills
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.toEntity
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.toModel
import ru.boringowl.itroadmap.domain.model.competence.Route
import javax.inject.Inject

class RouteLocalDataSource @Inject constructor(private val dao: RouteDao) {
    suspend fun insert(item: Route) = dao.insert(item.toEntity())
    fun insert(items: List<Route>) = dao.insert(items.map(Route::toEntity))
    suspend fun update(item: Route) = dao.update(item.toEntity())
    suspend fun delete(item: Route) = dao.delete(item.toEntity())
    suspend fun delete(id: Int) = dao.delete(id)
    fun get(): Flow<List<Route>> = dao.get().map { it.map(RouteEntity::toModel) }
    suspend fun delete() = dao.delete()
    fun isExist(id : Int) : Boolean = dao.isExist(id)
    suspend fun get(id: Int): Route? = dao.get(id)?.toModel()
    suspend fun getWithSkills(id: Int): RouteWithSkills? = dao.getWithSkills(id)
    suspend fun refresh(items: List<Route>) = dao.insertAndDelete(items.map(Route::toEntity))

}