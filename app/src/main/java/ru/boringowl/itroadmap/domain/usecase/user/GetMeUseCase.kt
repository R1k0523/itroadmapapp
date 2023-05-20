package ru.boringowl.itroadmap.domain.usecase.user

import ru.boringowl.itroadmap.domain.model.FlowResult
import ru.boringowl.itroadmap.domain.model.user.User

interface GetMeUseCase {
    suspend operator fun invoke(): FlowResult<User>
}