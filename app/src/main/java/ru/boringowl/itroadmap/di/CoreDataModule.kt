package ru.boringowl.itroadmap.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import ru.boringowl.itroadmap.data.common.AppConfig
import ru.boringowl.itroadmap.data.network.base.AuthInterceptor
import ru.boringowl.itroadmap.data.persistence.datastore.extenstions.dataStore
import ru.boringowl.itroadmap.data.persistence.datastore.storage.ThemeStorage
import ru.boringowl.itroadmap.data.persistence.datastore.storage.ThemeStorageImpl
import ru.boringowl.itroadmap.data.persistence.datastore.storage.TokenStorage
import ru.boringowl.itroadmap.data.usecase.theme.GetThemeUseCaseImpl
import ru.boringowl.itroadmap.data.usecase.theme.SetThemeUseCaseImpl
import ru.boringowl.itroadmap.domain.usecase.theme.GetThemeUseCase
import ru.boringowl.itroadmap.domain.usecase.theme.SetThemeUseCase
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
interface CoreDataModule {

    @Binds
    fun themeStorage(storage: ThemeStorageImpl): ThemeStorage

    @Binds
    fun getThemeUseCase(storage: GetThemeUseCaseImpl): GetThemeUseCase

    @Binds
    fun setThemeUseCase(storage: SetThemeUseCaseImpl): SetThemeUseCase

    companion object {
        @Provides
        fun dataStore(@ApplicationContext context: Context) = context.dataStore

        @Provides
        fun json() = Json { ignoreUnknownKeys = true }

        @Provides
        fun converterFactory(json: Json): Converter.Factory {
            val mediaType = "application/json".toMediaType()
            return json.asConverterFactory(mediaType)
        }

        @Provides
        @NoAuthClient
        fun okhttpClient(dataStore: TokenStorage) =
            OkHttpClient.Builder()
                .readTimeout(90, TimeUnit.SECONDS)
                .writeTimeout(90, TimeUnit.SECONDS)
                .addInterceptor(AuthInterceptor(dataStore))
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

        @Provides
        @NoAuthClient
        fun retrofit(
            @NoAuthClient client: OkHttpClient,
            factory: Converter.Factory,
        ) = Retrofit.Builder()
            .baseUrl(AppConfig.baseUrl)
            .addConverterFactory(factory)
            .client(client)
            .build()
    }
}

