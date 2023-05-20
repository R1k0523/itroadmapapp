package ru.boringowl.itroadmap.data.persistence.room.competence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.data.persistence.room.competence.entity.SkillTodoEntity
import java.util.*

@Dao
interface SkillTodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SkillTodoEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<SkillTodoEntity>)
    @Update
    suspend fun update(item: SkillTodoEntity)
    @Delete
    suspend fun delete(item: SkillTodoEntity)
    @Query("DELETE FROM skilltodos WHERE skill_todo_id = :id")
    suspend fun delete(id: UUID)

    @Query("DELETE FROM skilltodos")
    suspend fun delete()

    @Query("SELECT * FROM skilltodos WHERE skill_todo_id = :id")
    suspend fun get(id: UUID): SkillTodoEntity?

    @Query("SELECT EXISTS(SELECT * FROM skilltodos WHERE skill_todo_id = :id)")
    fun isExist(id : UUID) : Boolean

    @Query("SELECT * FROM skilltodos WHERE todo = :todo")
    fun getByTodo(todo: UUID): Flow<List<SkillTodoEntity>>

    @Query("SELECT * FROM skilltodos WHERE uploaded = 0")
    suspend fun getNotUploaded(): List<SkillTodoEntity>

    @Transaction
    suspend fun insertAndDelete(items: List<SkillTodoEntity>) {
        delete()
        insert(items)
    }
}