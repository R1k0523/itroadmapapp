package ru.boringowl.itroadmap.data.usecase.skilltodo

import ru.boringowl.itroadmap.data.repository.TodoSkillRepository
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.model.competence.SkillTodo
import ru.boringowl.itroadmap.domain.usecase.skilltodo.UpdateSkillTodoUseCase
import javax.inject.Inject

class UpdateSkillTodoUseCaseImpl @Inject constructor(
    private val repo: TodoSkillRepository,
) : UpdateSkillTodoUseCase {
    override suspend operator fun invoke(skillTodo: SkillTodo): BaseResult<Unit> = repo.update(skillTodo).cast{}
}