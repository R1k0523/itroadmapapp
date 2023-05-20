package ru.boringowl.itroadmap.data.persistence.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.boringowl.itroadmap.data.persistence.room.competence.dao.*
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.*
import ru.boringowl.itroadmap.data.persistence.room.converters.RoomConverters
import ru.boringowl.itroadmap.data.persistence.room.user.dao.UserDao
import ru.boringowl.itroadmap.data.persistence.room.user.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        BookPostEntity::class,
        BookPostKeys::class,
        HackathonEntity::class,
        HackathonKeys::class,
        RouteEntity::class,
        SkillEntity::class,
        SkillRemoteKeys::class,
        SkillTodoEntity::class,
        TodoEntity::class,
   ],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun bookKeyDao(): BookKeyDao
    abstract fun bookPostDao(): BookPostDao
    abstract fun hackathonDao(): HackathonDao
    abstract fun hackathonKeyDao(): HackathonKeyDao
    abstract fun routeDao(): RouteDao
    abstract fun skillDao(): SkillDao
    abstract fun skillKeyDao(): SkillKeyDao
    abstract fun skillTodoDao(): SkillTodoDao
    abstract fun todoDao(): TodoDao

    companion object {
        const val DATABASE_NAME = "roadmap_db"
    }
}