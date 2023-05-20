package ru.boringowl.itroadmap.domain.model.competence


import java.util.UUID

data class Todo(
    var todoId: UUID,
    var header: String = "",
    var user: String? = null,
    var skills: List<SkillTodo> = listOf(),
    var ready: Int = 0,
    var full: Int = 0,
)
