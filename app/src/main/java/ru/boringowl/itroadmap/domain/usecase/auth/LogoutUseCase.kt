package ru.boringowl.itroadmap.domain.usecase.auth

import ru.boringowl.itroadmap.domain.model.BaseResult

interface LogoutUseCase {
    suspend operator fun invoke(): BaseResult<Unit>
}