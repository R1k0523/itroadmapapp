package ru.boringowl.itroadmap.data.persistence.datastore.storage

import kotlinx.coroutines.flow.Flow

interface TokenStorage {
    fun token(): Flow<String>
    suspend fun setToken(token: String)
    suspend fun deleteToken()
    fun refreshToken(): Flow<String>
    suspend fun setRefreshToken(token: String)
    suspend fun deleteRefreshToken()
}