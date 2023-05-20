package ru.boringowl.itroadmap.domain.usecase.skilltodo

import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.model.competence.SkillTodo

interface UpdateSkillTodoUseCase {
    suspend operator fun invoke(skillTodo: SkillTodo): BaseResult<Unit>
}