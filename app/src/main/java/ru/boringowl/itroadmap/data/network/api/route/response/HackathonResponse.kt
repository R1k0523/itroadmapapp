package ru.boringowl.itroadmap.data.network.api.route.response

import kotlinx.serialization.Serializable
import ru.boringowl.itroadmap.domain.model.competence.Hackathon
import java.util.UUID

@Serializable
class HackathonResponse(
    var hackId: String,
    var hackTitle: String = "",
    var hackDescription: String = "",
    var publishDate: String = "",
    var source: String = "",
    var date: String? = "",
    var registration: String? = "",
    var focus: String? = "",
    var prize: String? = "",
    var routes: String? = "",
    var terms: String? = "",
    var organization: String? = "",
    var imageUrl: String? = "",
)

fun HackathonResponse.toModel() = Hackathon(
    UUID.fromString(hackId),
    hackTitle,
    hackDescription,
    publishDate,
    source,
    date,
    registration,
    focus,
    prize,
    routes,
    terms,
    organization,
    imageUrl,
)