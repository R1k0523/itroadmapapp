package ru.boringowl.itroadmap.data.network.api.auth.datasource

import ru.boringowl.itroadmap.data.network.api.auth.client.AuthApi
import ru.boringowl.itroadmap.data.network.api.auth.request.NewPasswordRequest
import ru.boringowl.itroadmap.data.network.api.auth.request.RefreshTokenRequest
import ru.boringowl.itroadmap.data.network.api.auth.request.ResetPasswordRequest
import ru.boringowl.itroadmap.data.network.api.auth.request.SignInRequest
import ru.boringowl.itroadmap.data.network.api.auth.request.SignUpRequest
import ru.boringowl.itroadmap.data.network.api.auth.request.TokenForResetPasswordRequest
import ru.boringowl.itroadmap.data.network.api.auth.response.toToken
import ru.boringowl.itroadmap.data.network.base.ResponseHandler
import ru.boringowl.itroadmap.domain.model.user.Token
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val api: AuthApi,
    private val handler: ResponseHandler,
) {
    suspend fun signIn(request: SignInRequest): Token = handler.process { api.signIn(request) }.toToken()
    suspend fun signUp(request: SignUpRequest) = handler.process { api.signUp(request) }
    suspend fun refresh(request: RefreshTokenRequest): Token = handler.process { api.refresh(request) }.toToken()
    suspend fun requestReset(request: ResetPasswordRequest) = handler.process { api.requestReset(request) }
    suspend fun getTokenForReset(request: TokenForResetPasswordRequest): Token = handler.process { api.getTokenForReset(request) }.toToken()
    suspend fun resetPassword(resetToken: String, request: NewPasswordRequest): Token =
        handler.process { api.resetPassword("Bearer $resetToken", request) }.toToken()
}