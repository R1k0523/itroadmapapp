package ru.boringowl.itroadmap.data.usecase.auth

import ru.boringowl.itroadmap.data.network.api.auth.request.SignInRequest
import ru.boringowl.itroadmap.data.repository.AuthRepository
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.usecase.auth.SignInUseCase
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(
    private val repo: AuthRepository
) : SignInUseCase {
    override suspend operator fun invoke(request: SignInRequest): BaseResult<Unit> =
        repo.signIn(request)
}