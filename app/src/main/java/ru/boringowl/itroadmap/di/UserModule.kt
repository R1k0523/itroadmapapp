package ru.boringowl.itroadmap.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.boringowl.itroadmap.data.network.api.user.client.UserApi
import ru.boringowl.itroadmap.data.persistence.room.db.LocalDatabase
import ru.boringowl.itroadmap.data.persistence.room.user.datasource.UserLocalDataSource
import ru.boringowl.itroadmap.data.usecase.user.GetMeUseCaseImpl
import ru.boringowl.itroadmap.domain.usecase.user.GetMeUseCase

@Module
@InstallIn(SingletonComponent::class)
interface UserModule {

    @Binds
    fun getMeUseCase(useCase: GetMeUseCaseImpl): GetMeUseCase

    companion object {
        @Provides
        fun userApi(@AuthClient client: Retrofit): UserApi = client.create(UserApi::class.java)

        @Provides
        fun userDB(@ApplicationContext context: Context): LocalDatabase =
            Room.databaseBuilder(
                context,
                LocalDatabase::class.java,
                LocalDatabase.DATABASE_NAME
            ).build()

        @Provides
        fun userLocalDataSource(db: LocalDatabase) = UserLocalDataSource(db.userDao())
    }
}