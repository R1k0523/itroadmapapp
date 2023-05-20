package ru.boringowl.itroadmap.data.persistence.datastore.extenstions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.boringowl.itroadmap.data.persistence.datastore.util.StringEncryptDecryptUtil


suspend inline fun DataStore<Preferences>.secureEdit(
    value: String,
    security: StringEncryptDecryptUtil,
    crossinline editStore: (MutablePreferences, String) -> Unit
) {
    edit {
        val encryptedValue =
            security.encryptData(value)
        editStore(it, encryptedValue)
    }
}


inline fun Flow<Preferences>.secureMap(
    security: StringEncryptDecryptUtil,
    crossinline fetchValue: (value: Preferences) -> String
): Flow<String> {
    return map { f ->
        val value = fetchValue(f)
        if (value.isNotEmpty())
            security.decryptData(value)
        else
            value
    }
}