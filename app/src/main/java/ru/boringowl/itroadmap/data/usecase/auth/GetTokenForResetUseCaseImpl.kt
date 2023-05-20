package ru.boringowl.itroadmap.data.usecase.auth

import ru.boringowl.itroadmap.data.network.api.auth.request.TokenForResetPasswordRequest
import ru.boringowl.itroadmap.data.repository.AuthRepository
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.usecase.auth.GetTokenForResetUseCase
import javax.inject.Inject

class GetTokenForResetUseCaseImpl @Inject constructor(
    private val repo: AuthRepository
) : GetTokenForResetUseCase {
    override suspend operator fun invoke(email: String, code: String): BaseResult<String> =
        repo.getTokenForReset(TokenForResetPasswordRequest(email, code))
}