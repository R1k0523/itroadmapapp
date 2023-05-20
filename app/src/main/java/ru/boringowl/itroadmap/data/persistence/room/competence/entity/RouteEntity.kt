package ru.boringowl.itroadmap.data.persistence.room.competence.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import ru.boringowl.itroadmap.domain.model.competence.Route


@Entity(tableName = "routes")
class RouteEntity(
    @PrimaryKey
    @ColumnInfo(name="route_id")
    var routeId: Int,
    @ColumnInfo(name="route_name")
    var routeName: String = "",
    @ColumnInfo(name="route_description")
    var routeDescription: String = "",
    @ColumnInfo(name="resumes_count")
    var resumesCount: Int = 0,
    @ColumnInfo(name="vacancies_count")
    var vacanciesCount: Int = 0,
)
fun RouteEntity.toModel(): Route = Route(
    routeId = routeId,
    routeName = routeName,
    routeDescription = routeDescription,
    resumesCount = resumesCount,
    vacanciesCount = vacanciesCount,
)

fun Route.toEntity(): RouteEntity = RouteEntity(
    routeId = routeId,
    routeName = routeName,
    routeDescription = routeDescription,
    resumesCount = resumesCount,
    vacanciesCount = vacanciesCount,
)


data class RouteWithSkills(
    @Embedded val route: RouteEntity,
    @Relation(
        parentColumn = "route_id",
        entityColumn = "skill_id"
    )
    val skills: List<SkillEntity>
)