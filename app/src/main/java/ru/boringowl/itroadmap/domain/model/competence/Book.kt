package ru.boringowl.itroadmap.domain.model.competence

import java.util.Locale
import java.util.UUID

class BookPost(
    var bookPostId: UUID,
    var routeId: Int? = null,
    var description: String = "",
    var books: List<BookInfo> = listOf()
)

data class BookInfo (
    var url: String = "",
    var filename: String = "",
    var size: Int = 0
) {
    fun sizeString(): String {
        val sizeKb = size.toDouble() / 1000
        return if (sizeKb > 1000) "${(sizeKb / 1000).format(2)} МБайт"
        else "${sizeKb.format(2)} КБайт"
    }
}

fun Double.format(digits:Int) = String.Companion.format(
    Locale.ENGLISH,
    "%#.${digits}f",
    this
)