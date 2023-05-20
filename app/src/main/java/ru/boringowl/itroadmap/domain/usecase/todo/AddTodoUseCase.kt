package ru.boringowl.itroadmap.domain.usecase.todo

import ru.boringowl.itroadmap.domain.model.BaseResult

interface AddTodoUseCase {
    suspend operator fun invoke(routeId: Int, todoName: String): BaseResult<Unit>
}