package ru.boringowl.itroadmap.data.persistence.room.competence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.boringowl.itroadmap.domain.model.competence.BookInfo
import ru.boringowl.itroadmap.domain.model.competence.BookPost
import java.util.UUID

@Entity(tableName = "book_posts")
class BookPostEntity(
    @PrimaryKey
    @ColumnInfo(name="book_post_id")
    var bookPostId: UUID,
    @ColumnInfo(name="route_id")
    var routeId: Int? = null,
    @ColumnInfo(name="description")
    var description: String = "",
    @ColumnInfo(name="books")
    var books: List<BookInfo> = listOf()
)


fun BookPostEntity.toModel(): BookPost = BookPost(
    bookPostId = bookPostId,
    routeId = routeId,
    description = description,
    books = books,
)

fun BookPost.toEntity() = BookPostEntity(
    bookPostId = bookPostId,
    routeId = routeId,
    description = description,
    books = books,
)