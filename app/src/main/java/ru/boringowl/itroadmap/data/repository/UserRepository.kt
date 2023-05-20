package ru.boringowl.itroadmap.data.repository

import ru.boringowl.itroadmap.data.network.api.auth.response.toToken
import ru.boringowl.itroadmap.data.network.api.user.datasource.UserRemoteDataSource
import ru.boringowl.itroadmap.data.network.api.user.request.FriendRequest
import ru.boringowl.itroadmap.data.network.api.user.request.PhotoRequest
import ru.boringowl.itroadmap.data.network.api.user.request.UpdatePasswordRequest
import ru.boringowl.itroadmap.data.network.api.user.request.UpdateRequest
import ru.boringowl.itroadmap.data.network.base.RequestHandler
import ru.boringowl.itroadmap.data.persistence.room.user.datasource.UserLocalDataSource
import ru.boringowl.itroadmap.domain.model.BaseResult
import ru.boringowl.itroadmap.domain.model.FlowResult
import ru.boringowl.itroadmap.domain.model.user.User
import java.util.UUID
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserLocalDataSource,
    private val handler: RequestHandler,
    private val authRepo: AuthRepository
) {
    suspend fun me(): FlowResult<User> {
        return handler.execute(
            processRequest = { remoteDataSource.me() },
            onAfter = {
                localDataSource.save(it)
            },
            onResultSaved = { localDataSource.get() },
            onError = { authRepo.logout() }
        )
    }
    suspend fun setPhoto(request: PhotoRequest): BaseResult<Unit> {
        return handler.execute { remoteDataSource.setPhoto(request) }.cast {}
    }

    suspend fun search(query: String) = handler.execute { remoteDataSource.search(query) }
    suspend fun findById(id: UUID) = handler.execute { remoteDataSource.findById(id) }
    suspend fun verify() = handler.execute { remoteDataSource.verify() }
    suspend fun activate(code: String) = handler.execute { remoteDataSource.activate(code) }
    suspend fun update(request: UpdateRequest) = handler.execute(
        processRequest = { remoteDataSource.update(request) },
        onAfter = { localDataSource.save(it) },
        onResultSaved = { localDataSource.get() }
    )
    suspend fun updatePassword(request: UpdatePasswordRequest) = authRepo.handleToken(
        handler.execute { remoteDataSource.updatePassword(request).toToken() }
    )
    suspend fun friend(dto: FriendRequest) = handler.execute { remoteDataSource.friend(dto) }
    suspend fun friends(id: UUID) = handler.execute { remoteDataSource.friends(id) }
    suspend fun followers(id: UUID) = handler.execute { remoteDataSource.followers(id) }
    suspend fun follows(id: UUID) = handler.execute { remoteDataSource.follows(id) }
}
