package ru.boringowl.itroadmap.domain.usecase.theme

import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.domain.model.theme.UITheme

interface GetThemeUseCase {
    suspend operator fun invoke(): Flow<UITheme>
}