package ru.boringowl.itroadmap.domain.usecase.auth

import ru.boringowl.itroadmap.domain.model.BaseResult

interface RequestResetUseCase {
    suspend operator fun invoke(email: String): BaseResult<Unit>
}