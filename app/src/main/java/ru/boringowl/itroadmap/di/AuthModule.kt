package ru.boringowl.itroadmap.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import ru.boringowl.itroadmap.data.common.AppConfig
import ru.boringowl.itroadmap.data.network.api.auth.AuthAuthenticator
import ru.boringowl.itroadmap.data.network.api.auth.client.AuthApi
import ru.boringowl.itroadmap.data.network.api.auth.datasource.AuthRemoteDataSource
import ru.boringowl.itroadmap.data.persistence.datastore.storage.TokenStorage
import ru.boringowl.itroadmap.data.persistence.datastore.storage.TokenStorageImpl
import ru.boringowl.itroadmap.data.usecase.auth.*
import ru.boringowl.itroadmap.domain.usecase.auth.*

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {
    @Binds
    fun getTokenForResetUseCase(useCase: GetTokenForResetUseCaseImpl): GetTokenForResetUseCase
    @Binds
    fun requestResetUseCase(useCase: RequestResetUseCaseImpl): RequestResetUseCase
    @Binds
    fun resetPasswordUseCase(useCase: ResetPasswordUseCaseImpl): ResetPasswordUseCase
    @Binds
    fun signInUseCase(useCase: SignInUseCaseImpl): SignInUseCase
    @Binds
    fun signUpUseCase(useCase: SignUpUseCaseImpl): SignUpUseCase
    @Binds
    fun logoutUseCase(useCase: LogoutUseCaseImpl): LogoutUseCase
    @Binds
    fun isSignedInUseCase(useCase: IsSignedInUseCaseImpl): IsSignedInUseCase

    @Binds
    fun tokenStorage(storage: TokenStorageImpl): TokenStorage

    companion object {
        @Provides
        fun authApi(@NoAuthClient client: Retrofit): AuthApi = client.create(AuthApi::class.java)

        @Provides
        @AuthClient
        fun authAuthenticator(
            storage: TokenStorage,
            dataSource: AuthRemoteDataSource): AuthAuthenticator = AuthAuthenticator(
            storage, dataSource
        )
        @Provides
        @AuthClient
        fun authOkHttpClient(
            @NoAuthClient client: OkHttpClient,
            @AuthClient authenticator: AuthAuthenticator
        ) = client.newBuilder().authenticator(authenticator).build()

        @Provides
        @AuthClient
        fun authRetrofit(
            @AuthClient client: OkHttpClient,
            factory: Converter.Factory,
        ) = Retrofit.Builder()
            .baseUrl(AppConfig.baseUrl)
            .addConverterFactory(factory)
            .client(client)
            .build()
    }
}
