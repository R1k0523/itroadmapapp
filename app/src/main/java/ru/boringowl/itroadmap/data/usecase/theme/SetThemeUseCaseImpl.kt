package ru.boringowl.itroadmap.data.usecase.theme

import ru.boringowl.itroadmap.data.persistence.datastore.storage.ThemeStorage
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.model.theme.UITheme
import ru.boringowl.itroadmap.domain.usecase.theme.SetThemeUseCase
import javax.inject.Inject

class SetThemeUseCaseImpl @Inject constructor(
    val storage: ThemeStorage
) : SetThemeUseCase {
    override suspend operator fun invoke(theme: UITheme): BaseResult<Unit> {
        return BaseResult.Success(storage.setTheme(theme))
    }
}