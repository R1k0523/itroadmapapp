package ru.boringowl.itroadmap.data.persistence.room.competence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "skill_keys")
class SkillRemoteKeys(
    @PrimaryKey
    @ColumnInfo(name="id")
    var id: UUID,
    @ColumnInfo(name="prev")
    var prev: Int = 1,
    @ColumnInfo(name="next")
    var next: Int = 1,
)