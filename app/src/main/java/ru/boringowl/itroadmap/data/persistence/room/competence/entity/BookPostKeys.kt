package ru.boringowl.itroadmap.data.persistence.room.competence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "book_keys")
class BookPostKeys(
    @PrimaryKey
    @ColumnInfo(name="id")
    var id: UUID,
    @ColumnInfo(name="prev")
    var prev: Int? = null,
    @ColumnInfo(name="next")
    var next: Int? = null,
)
