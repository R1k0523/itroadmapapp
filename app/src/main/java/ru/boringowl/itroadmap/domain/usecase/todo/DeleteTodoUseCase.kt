package ru.boringowl.itroadmap.domain.usecase.todo

import ru.boringowl.itroadmap.domain.model.BaseResult
import java.util.UUID

interface DeleteTodoUseCase {
    suspend operator fun invoke(id: UUID): BaseResult<Unit>
}