package ru.boringowl.itroadmap.data.persistence.room.user.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.boringowl.itroadmap.data.persistence.room.user.dao.UserDao
import ru.boringowl.itroadmap.data.persistence.room.user.entity.UserEntity
import ru.boringowl.itroadmap.data.persistence.room.user.mappers.toEntity
import ru.boringowl.itroadmap.data.persistence.room.user.mappers.toModel
import ru.boringowl.itroadmap.domain.model.user.User
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(private val dao: UserDao) {
    fun get(): Flow<User?> = dao.get().map { it?.toModel() }
    suspend fun save(model: User) { dao.insertAndDelete(model.toEntity()) }
    suspend fun delete(id: String) = dao.delete(id)
    suspend fun delete() = dao.delete()
    suspend fun insert(item: UserEntity) = dao.insert(item)
    fun insert(items: List<UserEntity>) = dao.insert(items)
    suspend fun update(item: UserEntity) = dao.update(item)
}