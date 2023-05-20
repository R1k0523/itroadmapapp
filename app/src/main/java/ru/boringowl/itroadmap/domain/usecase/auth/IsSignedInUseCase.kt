package ru.boringowl.itroadmap.domain.usecase.auth

import kotlinx.coroutines.flow.Flow

interface IsSignedInUseCase {
    suspend operator fun invoke(): Flow<Boolean>
}