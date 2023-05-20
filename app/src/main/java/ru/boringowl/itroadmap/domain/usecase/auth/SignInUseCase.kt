package ru.boringowl.itroadmap.domain.usecase.auth

import ru.boringowl.itroadmap.data.network.api.auth.request.SignInRequest
import ru.boringowl.itroadmap.domain.model.BaseResult


interface SignInUseCase {
    suspend operator fun invoke(request: SignInRequest): BaseResult<Unit>
}