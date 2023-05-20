package ru.boringowl.itroadmap.data.usecase.auth

import ru.boringowl.itroadmap.data.network.api.auth.request.ResetPasswordRequest
import ru.boringowl.itroadmap.data.repository.AuthRepository
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.usecase.auth.RequestResetUseCase
import javax.inject.Inject

class RequestResetUseCaseImpl @Inject constructor(
    private val repo: AuthRepository
) : RequestResetUseCase {
    override suspend operator fun invoke(email: String): BaseResult<Unit> =
        repo.requestReset(ResetPasswordRequest(email))
}