package ru.boringowl.itroadmap.domain.model.competence

import java.util.UUID

class Hackathon(
    var hackId: UUID,
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