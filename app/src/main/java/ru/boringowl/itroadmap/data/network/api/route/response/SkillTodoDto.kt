package ru.boringowl.itroadmap.data.network.api.route.response


import kotlinx.serialization.Serializable
import ru.boringowl.itroadmap.domain.model.competence.SkillTodo
import java.util.UUID

@Serializable
class SkillTodoDto(
    var skillTodoId: String,
    var skillName: String = "",
    var manualName: String = "",
    var todo: TodoDto? = null,
    var progress: Int = 0,
    var necessity: Int = 0,
    var notes: String = "",
    var binaryProgress: Boolean = false,
    var favorite: Boolean = false,
)


fun SkillTodoDto.toModel() = SkillTodo(
    UUID.fromString(skillTodoId),
    skillName,
    manualName,
    null,
    progress,
    necessity,
    notes,
    binaryProgress,
    favorite,
)

fun SkillTodo.toDto() = SkillTodoDto(
    skillTodoId.toString(),
    skillName,
    manualName,
    null,
    progress,
    necessity,
    notes,
    binaryProgress,
    favorite,
)