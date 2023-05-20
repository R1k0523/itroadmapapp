package ru.boringowl.itroadmap.data.persistence.room.competence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.SkillRemoteKeys
import java.util.*


@Dao
interface SkillKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SkillRemoteKeys)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<SkillRemoteKeys>)
    @Update
    suspend fun update(item: SkillRemoteKeys)
    @Delete
    suspend fun delete(item: SkillRemoteKeys)    @Query("DELETE FROM skill_keys")
    suspend fun delete()
    @Query("SELECT * FROM skill_keys WHERE id = :id")
    suspend fun get(id: UUID): SkillRemoteKeys?
}