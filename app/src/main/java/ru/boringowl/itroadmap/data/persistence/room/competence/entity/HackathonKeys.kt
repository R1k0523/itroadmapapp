package ru.boringowl.itroadmap.data.persistence.room.competence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "hackathon_keys")
class HackathonKeys(
    @PrimaryKey
    @ColumnInfo(name="id")
    var id: UUID,
    @ColumnInfo(name="prev")
    var prev: Int? = null,
    @ColumnInfo(name="next")
    var next: Int? = null,
)
