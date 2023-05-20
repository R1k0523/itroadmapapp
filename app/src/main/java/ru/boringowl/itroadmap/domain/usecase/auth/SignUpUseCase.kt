package ru.boringowl.itroadmap.domain.usecase.auth

import ru.boringowl.itroadmap.data.network.api.auth.request.SignUpRequest
import ru.boringowl.itroadmap.domain.model.BaseResult

interface SignUpUseCase {
    suspend operator fun invoke(request: SignUpRequest): BaseResult<Unit>
}