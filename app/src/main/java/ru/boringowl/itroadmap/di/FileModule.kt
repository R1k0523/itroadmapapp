package ru.boringowl.itroadmap.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.boringowl.itroadmap.data.network.api.file.client.FileApi
import ru.boringowl.itroadmap.data.usecase.auth.*
import ru.boringowl.itroadmap.data.usecase.file.UploadProfileImageUseCaseImpl
import ru.boringowl.itroadmap.domain.usecase.auth.*
import ru.boringowl.itroadmap.domain.usecase.file.UploadProfileImageUseCase

@Module
@InstallIn(SingletonComponent::class)
interface FileModule {
    @Binds
    fun uploadUserProfileUseCase(useCase: UploadProfileImageUseCaseImpl): UploadProfileImageUseCase

    companion object {
        @Provides
        fun fileApi(@AuthClient client: Retrofit): FileApi = client.create(FileApi::class.java)
    }
}