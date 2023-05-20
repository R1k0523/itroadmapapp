package ru.boringowl.itroadmap.data.usecase.user

import ru.boringowl.itroadmap.data.repository.UserRepository
import ru.boringowl.itroadmap.domain.model.FlowResult
import ru.boringowl.itroadmap.domain.model.user.User
import ru.boringowl.itroadmap.domain.usecase.user.GetMeUseCase
import javax.inject.Inject

class GetMeUseCaseImpl @Inject constructor(
    private val repo: UserRepository
) : GetMeUseCase {
    override suspend operator fun invoke(): FlowResult<User> =
        repo.me()
}