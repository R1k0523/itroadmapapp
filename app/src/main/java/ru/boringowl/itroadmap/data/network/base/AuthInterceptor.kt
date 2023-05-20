package ru.boringowl.itroadmap.data.network.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import ru.boringowl.itroadmap.data.persistence.datastore.storage.TokenStorage

class AuthInterceptor(private val dataStore: TokenStorage) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = runBlocking { dataStore.token().first() }
        if (token.isNotEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        val response = chain.proceed(requestBuilder.build())
        if (response.code == 401) {
            runBlocking {
                launch(Dispatchers.IO) {
                    dataStore.deleteToken()
                }
            }
        }
        return response
    }
}