package ru.boringowl.itroadmap.data.persistence.room.user.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "r_user")
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    val id: String,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "userRole")
    val role: String,
    @ColumnInfo(name = "imageId")
    val imageId: String? = null,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "fullName")
    val fullName: String,
    @ColumnInfo(name = "rating")
    val rating: Long,
    @ColumnInfo(name = "enabled")
    val enabled: Boolean,
)

