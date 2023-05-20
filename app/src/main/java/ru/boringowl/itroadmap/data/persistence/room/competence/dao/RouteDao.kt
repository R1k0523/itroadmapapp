package ru.boringowl.itroadmap.data.persistence.room.competence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.RouteEntity
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.RouteWithSkills

@Dao
interface RouteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RouteEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<RouteEntity>)
    @Update
    suspend fun update(item: RouteEntity)
    @Delete
    suspend fun delete(item: RouteEntity)
    @Query("DELETE FROM routes WHERE route_id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM routes")
    fun get(): Flow<List<RouteEntity>>

    @Query("DELETE FROM routes")
    suspend fun delete()

    @Query("SELECT EXISTS(SELECT * FROM routes WHERE route_id = :id)")
    fun isExist(id : Int) : Boolean

    @Query("SELECT * FROM routes WHERE route_id = :id")
    suspend fun get(id: Int): RouteEntity?

    @Query("SELECT * FROM routes WHERE route_id = :id")
    suspend fun getWithSkills(id: Int): RouteWithSkills?

    @Transaction
    suspend fun insertAndDelete(items: List<RouteEntity>) {
        delete()
        insert(items)
    }
}