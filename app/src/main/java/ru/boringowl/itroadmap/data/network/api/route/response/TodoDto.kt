package ru.boringowl.itroadmap.data.network.api.route.response


import kotlinx.serialization.Serializable
import ru.boringowl.itroadmap.domain.model.competence.SkillTodo
import ru.boringowl.itroadmap.domain.model.competence.Todo
import java.util.UUID

@Serializable
class TodoDto(
    var todoId: String,
    var header: String = "",
    var user: String? = null,
    var skills: List<SkillTodoDto> = listOf(),
    var ready: Int = 0,
    var full: Int = 0,
)


fun TodoDto.toModel() = Todo(
    UUID.fromString(todoId),
    header,
    user,
    skills.map(SkillTodoDto::toModel),
    ready,
    full,
)


fun Todo.toDto() = TodoDto(
    todoId.toString(),
    header,
    user,
    skills.map(SkillTodo::toDto),
    ready,
    full,
)