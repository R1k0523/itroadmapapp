package ru.boringowl.itroadmap.data.persistence.datastore.storage

import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.domain.model.theme.UITheme

interface ThemeStorage {
    fun theme(): Flow<UITheme>
    suspend fun setTheme(theme: UITheme)
}