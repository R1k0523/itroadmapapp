package ru.boringowl.itroadmap.domain.usecase.auth

import ru.boringowl.itroadmap.domain.model.BaseResult

interface ResetPasswordUseCase {
    suspend operator fun invoke(token: String, password: String, passwordRepeat: String): BaseResult<Unit>
}