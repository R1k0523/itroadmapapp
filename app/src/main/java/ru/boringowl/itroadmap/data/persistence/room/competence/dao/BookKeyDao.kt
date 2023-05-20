package ru.boringowl.itroadmap.data.persistence.room.competence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.BookPostKeys
import java.util.*


@Dao
interface BookKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: BookPostKeys)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<BookPostKeys>)
    @Update
    suspend fun update(item: BookPostKeys)
    @Delete
    suspend fun delete(item: BookPostKeys)
    @Query("DELETE FROM book_keys")
    suspend fun delete()

    @Query("SELECT * FROM book_keys WHERE id = :id")
    suspend fun get(id: UUID): BookPostKeys?
}