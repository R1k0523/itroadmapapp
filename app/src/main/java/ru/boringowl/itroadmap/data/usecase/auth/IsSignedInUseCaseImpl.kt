package ru.boringowl.itroadmap.data.usecase.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.boringowl.itroadmap.data.persistence.datastore.storage.TokenStorage
import ru.boringowl.itroadmap.domain.usecase.auth.IsSignedInUseCase
import javax.inject.Inject

class IsSignedInUseCaseImpl @Inject constructor(
    private val storage: TokenStorage
): IsSignedInUseCase {
    override suspend operator fun invoke(): Flow<Boolean> =
        storage.token().distinctUntilChanged().map { it.isNotEmpty() }
}