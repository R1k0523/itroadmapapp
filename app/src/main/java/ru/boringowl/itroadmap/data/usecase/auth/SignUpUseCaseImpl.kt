package ru.boringowl.itroadmap.data.usecase.auth

import ru.boringowl.itroadmap.data.network.api.auth.request.SignUpRequest
import ru.boringowl.itroadmap.data.repository.AuthRepository
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.usecase.auth.SignUpUseCase
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val repo: AuthRepository
) : SignUpUseCase {
    override suspend operator fun invoke(request: SignUpRequest): BaseResult<Unit> =
        repo.signUp(request)
}