package ru.boringowl.itroadmap.data.persistence.room.competence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.SkillEntity
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.SkillWithRoute
import java.util.*

@Dao
interface SkillDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SkillEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<SkillEntity>)
    @Update
    suspend fun update(item: SkillEntity)
    @Delete
    suspend fun delete(item: SkillEntity)
    @Query("DELETE FROM skills WHERE skill_id = :id")
    suspend fun delete(id: UUID)

    @Query("DELETE FROM skills WHERE route = :id")
    suspend fun deleteByRoute(id: Int)

    @Query("SELECT * FROM skills WHERE route = :id")
    fun getByRoute(id: Int): Flow<List<SkillWithRoute>>

    @Query("DELETE FROM skills")
    suspend fun delete()

    @Query("SELECT EXISTS(SELECT * FROM skills WHERE skill_id = :id)")
    fun isExist(id : Int) : Boolean

    @Query("SELECT * FROM skills WHERE skill_id = :id")
    suspend fun get(id: UUID): SkillEntity?

    @Transaction
    suspend fun insertAndDelete(items: List<SkillEntity>) {
        items.first().route_id?.let { deleteByRoute(it) }
        insert(items)
    }
}