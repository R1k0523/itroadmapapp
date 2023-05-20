package ru.boringowl.itroadmap.domain.usecase.skilltodo

import ru.boringowl.itroadmap.domain.model.FlowResult
import ru.boringowl.itroadmap.domain.model.competence.SkillTodo
import java.util.UUID

interface GetTodoSkillsUseCase {
    suspend operator fun invoke(todoId: UUID): FlowResult<List<SkillTodo>>
}