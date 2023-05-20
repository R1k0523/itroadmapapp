package ru.boringowl.itroadmap.data.persistence.room.converters

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ru.boringowl.itroadmap.domain.model.competence.BookInfo

object RoomConverters {
    private val gson = GsonBuilder().setLenient().create()
    @TypeConverter
    fun stringToBooksList(data: String): List<BookInfo> =
        gson.fromJson(data, object : TypeToken<List<BookInfo?>?>() {}.type)
    @TypeConverter
    fun listOfBooksToString(books: List<BookInfo>): String = gson.toJson(books)
}