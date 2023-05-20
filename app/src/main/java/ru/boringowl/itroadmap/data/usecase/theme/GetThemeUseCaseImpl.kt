package ru.boringowl.itroadmap.data.usecase.theme

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import ru.boringowl.itroadmap.data.persistence.datastore.storage.ThemeStorage
import ru.boringowl.itroadmap.domain.model.theme.UITheme
import ru.boringowl.itroadmap.domain.usecase.theme.GetThemeUseCase
import javax.inject.Inject

class GetThemeUseCaseImpl @Inject constructor(
    private val storage: ThemeStorage
) : GetThemeUseCase {
    override suspend operator fun invoke(): Flow<UITheme> =
        storage.theme().distinctUntilChanged()
}