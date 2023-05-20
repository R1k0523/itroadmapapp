package ru.boringowl.itroadmap.data.network.api.user.datasource

import ru.boringowl.itroadmap.data.network.api.user.client.UserApi
import ru.boringowl.itroadmap.data.network.api.user.request.FriendRequest
import ru.boringowl.itroadmap.data.network.api.user.request.PhotoRequest
import ru.boringowl.itroadmap.data.network.api.user.request.UpdatePasswordRequest
import ru.boringowl.itroadmap.data.network.api.user.request.UpdateRequest
import ru.boringowl.itroadmap.data.network.api.user.response.toUser
import ru.boringowl.itroadmap.data.network.base.ResponseHandler
import ru.boringowl.itroadmap.domain.model.user.User
import java.util.UUID
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val api: UserApi,
    private val handler: ResponseHandler,
) {
    suspend fun me(): User = handler.process { api.me() }.toUser()
    suspend fun setPhoto(request: PhotoRequest) = handler.process { api.setPhoto(request) }
    suspend fun search(query: String) = handler.process { api.search(query) }
    suspend fun findById(id: UUID) = handler.process { api.findById(id) }
    suspend fun verify() = handler.process { api.verify() }
    suspend fun activate(code: String) = handler.process { api.activate(code) }
    suspend fun update(request: UpdateRequest) = handler.process { api.update(request) }.toUser()
    suspend fun updatePassword(request: UpdatePasswordRequest) = handler.process { api.updatePassword(request) }
    suspend fun friend(dto: FriendRequest) = handler.process { api.friend(dto) }
    suspend fun friends(id: UUID) = handler.process { api.friends(id) }
    suspend fun followers(id: UUID) = handler.process { api.followers(id) }
    suspend fun follows(id: UUID) = handler.process { api.follows(id) }
}