package ru.boringowl.itroadmap.data.persistence.room.competence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.TodoEntity
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.TodoWithSkills
import java.util.*

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TodoEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<TodoEntity>)
    @Update
    suspend fun update(item: TodoEntity)
    @Delete
    suspend fun delete(item: TodoEntity)
    @Query("DELETE FROM todos WHERE todo_id = :id")
    suspend fun delete(id: UUID)

    @Query("SELECT * FROM todos")
    fun get(): Flow<List<TodoWithSkills>>

    @Query("SELECT * FROM todos")
    fun getList(): List<TodoEntity>

    @Query("DELETE FROM todos")
    suspend fun delete()

    @Query("SELECT EXISTS(SELECT * FROM todos WHERE todo_id = :id)")
    fun isExist(id : UUID) : Boolean

    @Query("SELECT * FROM todos WHERE todo_id = :id")
    fun get(id: UUID): Flow<TodoWithSkills?>

    @Query("SELECT * FROM todos WHERE todo_id = :id")
    fun getItem(id: UUID): TodoEntity

    @Query("SELECT * FROM todos WHERE uploaded = 0")
    suspend fun getNotUploaded(): List<TodoEntity>

    @Query("DELETE FROM todos WHERE todo_id = :id")
    suspend fun deleteById(id: UUID)

    @Transaction
    suspend fun insertAndDelete(items: List<TodoEntity>) {
        delete()
        insert(items)
    }
}