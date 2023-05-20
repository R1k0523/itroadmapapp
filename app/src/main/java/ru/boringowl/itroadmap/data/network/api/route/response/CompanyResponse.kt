package ru.boringowl.itroadmap.data.network.api.route.response

import kotlinx.serialization.Serializable
import ru.boringowl.itroadmap.domain.model.company.Company
import ru.boringowl.itroadmap.domain.model.company.CompanyRating

@Serializable
data class CompanyResponse(
    var uniqueName: String = "",
    var name: String = "",
    var description: String = "",
    var skills: List<String> = listOf(),
    var ratings: List<CompanyRatingResponse> = listOf(),
    var logo: String = ""
)

@Serializable
data class CompanyRatingResponse(
    var year: Long = 1980,
    var value: Double = 0.0
)

fun CompanyResponse.toModel() = Company(
    uniqueName = uniqueName,
    name = name,
    description = description,
    skills = skills,
    ratings = ratings.map(CompanyRatingResponse::toModel),
    logo = logo,
)

fun CompanyRatingResponse.toModel() = CompanyRating(
    year = year,
    value = value,
)