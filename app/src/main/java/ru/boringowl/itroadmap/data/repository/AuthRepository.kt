package ru.boringowl.itroadmap.data.repository

import ru.boringowl.itroadmap.R
import ru.boringowl.itroadmap.data.network.api.auth.datasource.AuthRemoteDataSource
import ru.boringowl.itroadmap.data.network.api.auth.request.NewPasswordRequest
import ru.boringowl.itroadmap.data.network.api.auth.request.RefreshTokenRequest
import ru.boringowl.itroadmap.data.network.api.auth.request.ResetPasswordRequest
import ru.boringowl.itroadmap.data.network.api.auth.request.SignInRequest
import ru.boringowl.itroadmap.data.network.api.auth.request.SignUpRequest
import ru.boringowl.itroadmap.data.network.api.auth.request.TokenForResetPasswordRequest
import ru.boringowl.itroadmap.data.network.base.RequestHandler
import ru.boringowl.itroadmap.data.persistence.datastore.storage.TokenStorage
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.model.user.Token
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val tokenStorage: TokenStorage,
    private val handler: RequestHandler
) {

    suspend fun signUp(request: SignUpRequest): BaseResult<Unit> {
        return handler.execute { remoteDataSource.signUp(request) }.cast {}
    }

    suspend fun signIn(request: SignInRequest): BaseResult<Unit> {
        val response = handler.execute { remoteDataSource.signIn(request) }
        return handleToken(response)
    }

    suspend fun refresh(request: RefreshTokenRequest): BaseResult<Unit> {
        val response = handler.execute { remoteDataSource.refresh(request) }
        return handleToken(response)
    }

    suspend fun requestReset(request: ResetPasswordRequest): BaseResult<Unit> {
        return handler.execute { remoteDataSource.requestReset(request) }.cast {}
    }

    suspend fun getTokenForReset(request: TokenForResetPasswordRequest): BaseResult<String> {
        val response = handler.execute { remoteDataSource.getTokenForReset(request) }
        return when(response) {
            is BaseResult.Success -> BaseResult.Success(response.data.accessToken)
            is BaseResult.Error -> BaseResult.Error(response.message)
            BaseResult.Loading -> BaseResult.Loading
        }
    }

    suspend fun resetPassword(token: String, request: NewPasswordRequest): BaseResult<Unit> {
        val response = handler.execute { remoteDataSource.resetPassword(token, request) }
        return handleToken(response)
    }


    suspend fun handleToken(response: BaseResult<Token>): BaseResult<Unit> {
        if (response is BaseResult.Success) {
            tokenStorage.setToken(response.data.accessToken)
            tokenStorage.setRefreshToken(response.data.refreshToken)
        }
        return response.cast {}
    }

    suspend fun logout(): BaseResult<Unit> {
        return try {
            tokenStorage.deleteToken()
            BaseResult.Success(Unit)
        } catch (e: Exception) {
            BaseResult.Error(messageId = R.string.error_unknown)
        }
    }
}
