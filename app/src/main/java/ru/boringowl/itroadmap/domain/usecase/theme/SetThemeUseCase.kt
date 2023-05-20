package ru.boringowl.itroadmap.domain.usecase.theme

import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.model.theme.UITheme

interface SetThemeUseCase {
    suspend operator fun invoke(theme: UITheme): BaseResult<Unit>
}