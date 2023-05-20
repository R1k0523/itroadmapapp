package ru.boringowl.itroadmap.domain.usecase.auth

import ru.boringowl.itroadmap.domain.model.BaseResult

interface GetTokenForResetUseCase {
    suspend operator fun invoke(email: String, code: String): BaseResult<String>
}