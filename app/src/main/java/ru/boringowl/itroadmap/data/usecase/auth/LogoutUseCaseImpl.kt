package ru.boringowl.itroadmap.data.usecase.auth

import ru.boringowl.itroadmap.data.repository.AuthRepository
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.usecase.auth.LogoutUseCase
import javax.inject.Inject

class LogoutUseCaseImpl @Inject constructor(
    private val repo: AuthRepository
) : LogoutUseCase {
    override suspend operator fun invoke(): BaseResult<Unit> =
        repo.logout()
}