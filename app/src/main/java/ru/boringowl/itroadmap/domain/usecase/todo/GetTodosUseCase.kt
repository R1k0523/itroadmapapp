package ru.boringowl.itroadmap.domain.usecase.todo

import ru.boringowl.itroadmap.domain.model.FlowResult
import ru.boringowl.itroadmap.domain.model.competence.Todo

interface GetTodosUseCase {
    suspend operator fun invoke(): FlowResult<List<Todo>>
}