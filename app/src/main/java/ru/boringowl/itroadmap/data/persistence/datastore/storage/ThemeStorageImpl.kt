package ru.boringowl.itroadmap.data.persistence.datastore.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.boringowl.itroadmap.domain.model.theme.UITheme
import javax.inject.Inject


class ThemeStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : ThemeStorage {

    private object PreferenceKeys {
        val SELECTED_THEME = stringPreferencesKey("theme")
    }

    override fun theme(): Flow<UITheme> = dataStore.data.map { prefs ->
        prefs[PreferenceKeys.SELECTED_THEME]?.let { UITheme.valueOf(it) } ?: UITheme.SYSTEM
    }

    override suspend fun setTheme(theme: UITheme) {
        dataStore.edit {
            it[PreferenceKeys.SELECTED_THEME] = theme.name
        }
    }
}