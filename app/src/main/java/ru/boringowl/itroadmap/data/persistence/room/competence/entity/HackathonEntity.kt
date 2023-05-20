package ru.boringowl.itroadmap.data.persistence.room.competence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.boringowl.itroadmap.domain.model.competence.Hackathon
import java.util.UUID

@Entity(tableName = "hackathons")
class HackathonEntity(
    @PrimaryKey
    @ColumnInfo(name="hack_id")
    var hackId: UUID,
    @ColumnInfo(name="hack_title")
    var hackTitle: String = "",
    @ColumnInfo(name="hack_description")
    var hackDescription: String = "",
    @ColumnInfo(name="publish_date")
    var publishDate: String,
    @ColumnInfo(name="source")
    var source: String = "",
    @ColumnInfo(name="date")
    var date: String? = "",
    @ColumnInfo(name="registration")
    var registration: String? = "",
    @ColumnInfo(name="focus")
    var focus: String? = "",
    @ColumnInfo(name="prize")
    var prize: String? = "",
    @ColumnInfo(name="routes")
    var routes: String? = "",
    @ColumnInfo(name="terms")
    var terms: String? = "",
    @ColumnInfo(name="organization")
    var organization: String? = "",
    @ColumnInfo(name="image_url")
    var imageUrl: String? = "",
)


fun HackathonEntity.toModel(): Hackathon = Hackathon(
    hackId = hackId,
    hackTitle = hackTitle,
    hackDescription = hackDescription,
    publishDate = publishDate,
    source = source,
    date = date,
    registration = registration,
    focus = focus,
    prize = prize,
    routes = routes,
    terms = terms,
    organization = organization,
    imageUrl = imageUrl,
)

fun Hackathon.toEntity(): HackathonEntity = HackathonEntity(
    hackId = hackId,
    hackTitle = hackTitle,
    hackDescription = hackDescription,
    publishDate = publishDate,
    source = source,
    date = date,
    registration = registration,
    focus = focus,
    prize = prize,
    routes = routes,
    terms = terms,
    organization = organization,
    imageUrl = imageUrl,
)