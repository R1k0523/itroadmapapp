package ru.boringowl.itroadmap.data.usecase.todo

import ru.boringowl.itroadmap.data.repository.TodoSkillRepository
import ru.boringowl.itroadmap.domain.model.FlowResult
import ru.boringowl.itroadmap.domain.model.competence.SkillTodo
import ru.boringowl.itroadmap.domain.usecase.skilltodo.GetTodoSkillsUseCase
import java.util.UUID
import javax.inject.Inject

class GetTodoSkillsUseCaseImpl @Inject constructor(
    private val repo: TodoSkillRepository
) : GetTodoSkillsUseCase {
    override suspend operator fun invoke(todoId: UUID): FlowResult<List<SkillTodo>> = repo.getSkills(todoId)
}