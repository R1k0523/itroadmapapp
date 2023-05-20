package ru.boringowl.itroadmap.data.usecase.todo

import ru.boringowl.itroadmap.data.repository.TodoRepository
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.usecase.todo.AddTodoUseCase
import javax.inject.Inject

class AddTodoUseCaseImpl @Inject constructor(
    private val repo: TodoRepository,
) : AddTodoUseCase {
    override suspend operator fun invoke(routeId: Int, todoName: String): BaseResult<Unit> {
        return repo.add(routeId, todoName)
    }
}