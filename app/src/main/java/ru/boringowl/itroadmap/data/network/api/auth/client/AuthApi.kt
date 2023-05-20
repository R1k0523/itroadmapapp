package ru.boringowl.itroadmap.data.network.api.auth.client

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ru.boringowl.itroadmap.data.network.api.auth.request.*
import ru.boringowl.itroadmap.data.network.api.auth.response.TokenResponse
import ru.boringowl.itroadmap.data.network.base.ConstantsServer


interface AuthApi {
    @POST("${ConstantsServer.authEndpoint}/signin")
    suspend fun signIn(@Body request: SignInRequest): Response<TokenResponse>
    @POST("${ConstantsServer.authEndpoint}/signup")
    suspend fun signUp(@Body request: SignUpRequest, ): Response<Unit>
    @POST("${ConstantsServer.authEndpoint}/refresh")
    suspend fun refresh(@Body request: RefreshTokenRequest): Response<TokenResponse>
    @POST("${ConstantsServer.authEndpoint}/reset/email")
    suspend fun requestReset(@Body request: ResetPasswordRequest): Response<Unit>
    @POST("${ConstantsServer.authEndpoint}/reset/code")
    suspend fun getTokenForReset(@Body request: TokenForResetPasswordRequest): Response<TokenResponse>
    @POST("${ConstantsServer.authEndpoint}/reset")
    suspend fun resetPassword(@Header("Authorization") token: String, @Body request: NewPasswordRequest): Response<TokenResponse>
}