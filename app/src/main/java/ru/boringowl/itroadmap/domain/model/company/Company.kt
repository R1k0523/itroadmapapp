package ru.boringowl.itroadmap.domain.model.company

data class Company(
    var uniqueName: String = "",
    var name: String = "",
    var description: String = "",
    var skills: List<String> = listOf(),
    var ratings: List<CompanyRating> = listOf(),
    var logo: String = ""
)

data class CompanyRating(
    var year: Long = 1980,
    var value: Double = 0.0
)