package ru.boringowl.itroadmap.data.persistence.datastore.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import ru.boringowl.itroadmap.data.persistence.datastore.extenstions.secureEdit
import ru.boringowl.itroadmap.data.persistence.datastore.extenstions.secureMap
import ru.boringowl.itroadmap.data.persistence.datastore.util.StringEncryptDecryptUtil
import javax.inject.Inject


class TokenStorageImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val security: StringEncryptDecryptUtil,
) : TokenStorage {

    private object PreferenceKeys {
        val TOKEN = stringPreferencesKey("token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    override fun token(): Flow<String> = dataStore.data.secureMap(security) {
        it[PreferenceKeys.TOKEN] ?: ""
    }

    override suspend fun setToken(token: String) {
        dataStore.secureEdit(token, security) { ds, encryptedValue ->
            ds[PreferenceKeys.TOKEN] = encryptedValue
        }
    }

    override suspend fun deleteToken() {
        dataStore.edit {
            it[PreferenceKeys.TOKEN] = ""
        }
    }

    override fun refreshToken(): Flow<String> = dataStore.data.secureMap(security) {
        it[PreferenceKeys.REFRESH_TOKEN] ?: ""
    }

    override suspend fun setRefreshToken(token: String) {
        dataStore.secureEdit(token, security) { ds, encryptedValue ->
            ds[PreferenceKeys.REFRESH_TOKEN] = encryptedValue
        }
    }

    override suspend fun deleteRefreshToken() {
        dataStore.edit {
            it[PreferenceKeys.REFRESH_TOKEN] = ""
        }
    }

}