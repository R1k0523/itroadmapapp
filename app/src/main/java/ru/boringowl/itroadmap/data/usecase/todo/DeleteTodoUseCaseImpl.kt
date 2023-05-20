package ru.boringowl.itroadmap.data.usecase.todo

import ru.boringowl.itroadmap.data.repository.TodoRepository
import ru.boringowl.itroadmap.domain.usecase.todo.DeleteTodoUseCase
import java.util.UUID
import javax.inject.Inject

class DeleteTodoUseCaseImpl @Inject constructor(
    private val repo: TodoRepository
) : DeleteTodoUseCase {
    override suspend operator fun invoke(id: UUID) = repo.delete(id)
}