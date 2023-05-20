package ru.boringowl.itroadmap.data.network.api.auth

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.boringowl.itroadmap.data.network.api.auth.datasource.AuthRemoteDataSource
import ru.boringowl.itroadmap.data.network.api.auth.request.RefreshTokenRequest
import ru.boringowl.itroadmap.data.persistence.datastore.storage.TokenStorage
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenStorage: TokenStorage,
    private val authRemoteDataSource: AuthRemoteDataSource,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request {
        return runBlocking {
            val token = tokenStorage.refreshToken().first()
            var newAccessToken: String? = null
            try {
                val newToken = authRemoteDataSource.refresh(RefreshTokenRequest(token))
                newAccessToken = newToken.accessToken
                tokenStorage.setToken(newAccessToken)
                tokenStorage.setRefreshToken(newToken.refreshToken)
            } catch (e: Exception) {
                tokenStorage.deleteToken()
                tokenStorage.deleteRefreshToken()

            }
            response.request.newBuilder()
                .header("Authorization", "Bearer $newAccessToken")
                .build()
        }
    }
}