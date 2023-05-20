package ru.boringowl.itroadmap.data.persistence.room.competence.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.HackathonEntity
import java.util.*


@Dao
interface HackathonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: HackathonEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<HackathonEntity>)
    @Update
    suspend fun update(item: HackathonEntity)
    @Delete
    suspend fun delete(item: HackathonEntity)
    @Query("DELETE FROM hackathons WHERE hack_id = :id")
    suspend fun delete(id: UUID)

    @Query("SELECT * FROM hackathons")
    fun get(): PagingSource<Int, HackathonEntity>

    @Query("DELETE FROM hackathons")
    suspend fun delete()

    @Query("SELECT EXISTS(SELECT * FROM hackathons WHERE hack_id = :id)")
    fun isExist(id : UUID) : Boolean

    @Query("SELECT * FROM hackathons WHERE hack_id = :id")
    suspend fun get(id: UUID): HackathonEntity?

    @Transaction
    suspend fun clearAndInsert(items: List<HackathonEntity>) {
        delete()
        insert(items)
    }
}