package ru.boringowl.itroadmap.data.usecase.auth

import ru.boringowl.itroadmap.data.network.api.auth.request.NewPasswordRequest
import ru.boringowl.itroadmap.data.repository.AuthRepository
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.usecase.auth.ResetPasswordUseCase
import javax.inject.Inject

class ResetPasswordUseCaseImpl @Inject constructor(
    private val repo: AuthRepository
) : ResetPasswordUseCase {
    override suspend operator fun invoke(token: String, password: String, passwordRepeat: String): BaseResult<Unit> =
        repo.resetPassword(token, NewPasswordRequest(password, passwordRepeat))
}