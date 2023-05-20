package ru.boringowl.itroadmap.data.persistence.room.user.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.data.persistence.room.user.entity.UserEntity


@Dao
interface UserDao {
    @Query("DELETE FROM r_user WHERE user_id = :id")
    suspend fun delete(id: String)
    @Query("SELECT * FROM r_user LIMIT 1")
    fun get(): Flow<UserEntity?>
    @Query("DELETE FROM r_user")
    suspend fun delete()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: UserEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<UserEntity>)
    @Update
    suspend fun update(item: UserEntity)
    @Transaction
    suspend fun insertAndDelete(item: UserEntity) {
        delete()
        insert(item)
    }
}