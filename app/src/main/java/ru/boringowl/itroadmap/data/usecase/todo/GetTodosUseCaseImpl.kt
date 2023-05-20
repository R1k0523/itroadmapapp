package ru.boringowl.itroadmap.data.usecase.todo

import ru.boringowl.itroadmap.data.repository.TodoRepository
import ru.boringowl.itroadmap.domain.model.FlowResult
import ru.boringowl.itroadmap.domain.model.competence.Todo
import ru.boringowl.itroadmap.domain.usecase.todo.GetTodosUseCase
import javax.inject.Inject

class GetTodosUseCaseImpl @Inject constructor(
    private val repo: TodoRepository
) : GetTodosUseCase {
    override suspend operator fun invoke(): FlowResult<List<Todo>> = repo.get()
}