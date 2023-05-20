package ru.boringowl.itroadmap.data.persistence.room.competence.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.BookPostEntity
import java.util.*

@Dao
interface BookPostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: BookPostEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<BookPostEntity>)
    @Update
    suspend fun update(item: BookPostEntity)
    @Delete
    suspend fun delete(item: BookPostEntity)
    @Query("DELETE FROM book_posts WHERE book_post_id = :id")
    suspend fun delete(id: UUID)

    @Query("SELECT * FROM book_posts WHERE route_id = :id")
    fun getByRoute(id: Int): PagingSource<Int, BookPostEntity>

    @Query("DELETE FROM book_posts")
    suspend fun delete()

    @Query("SELECT EXISTS(SELECT * FROM book_posts WHERE book_post_id = :id)")
    fun isExist(id : UUID) : Boolean

    @Query("SELECT * FROM book_posts WHERE book_post_id = :id")
    suspend fun get(id: UUID): BookPostEntity?
}