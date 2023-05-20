package ru.boringowl.itroadmap.data.persistence.room.competence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.HackathonKeys
import java.util.*


@Dao
interface HackathonKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: HackathonKeys)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<HackathonKeys>)
    @Update
    suspend fun update(item: HackathonKeys)
    @Delete
    suspend fun delete(item: HackathonKeys)
    @Query("DELETE FROM hackathon_keys")
    suspend fun delete()

    @Query("SELECT * FROM hackathon_keys WHERE id = :id")
    suspend fun get(id: UUID): HackathonKeys?
//
//    @Query("Select created_at From hackathon_keys Order By created_at DESC LIMIT 1")
//    suspend fun getCreationTime(): Long?
}