package ru.boringowl.itroadmap.data.network.api.user.client

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.boringowl.itroadmap.data.network.api.auth.request.*
import ru.boringowl.itroadmap.data.network.api.auth.response.TokenResponse
import ru.boringowl.itroadmap.data.network.api.user.request.FriendRequest
import ru.boringowl.itroadmap.data.network.api.user.request.PhotoRequest
import ru.boringowl.itroadmap.data.network.api.user.request.UpdatePasswordRequest
import ru.boringowl.itroadmap.data.network.api.user.request.UpdateRequest
import ru.boringowl.itroadmap.data.network.api.user.response.UserResponse
import ru.boringowl.itroadmap.data.network.base.ConstantsServer
import ru.boringowl.itroadmap.domain.model.user.UserShort
import java.util.UUID


interface UserApi {
    @GET("${ConstantsServer.usersEndpoint}/me")
    suspend fun me(): Response<UserResponse>

    @PATCH("${ConstantsServer.usersEndpoint}/photo")
    suspend fun setPhoto(
        @Body photoRequest: PhotoRequest
    ): Response<Unit>
    @GET(ConstantsServer.usersEndpoint)
    suspend fun search(@Query("query") query: String = ""): Response<List<UserResponse>>
    @GET("${ConstantsServer.usersEndpoint}/{id}")
    suspend fun findById(@Path("id") id: UUID): Response<UserResponse>
    @POST("${ConstantsServer.usersEndpoint}/verify")
    suspend fun verify(): Response<Unit>
    @POST("${ConstantsServer.usersEndpoint}/verify/{code}")
    suspend fun activate(@Path("code") code: String): Response<Unit>
    @PATCH(ConstantsServer.usersEndpoint)
    suspend fun update(@Body request: UpdateRequest): Response<UserResponse>
    @PATCH("${ConstantsServer.usersEndpoint}/password")
    suspend fun updatePassword(@Body request: UpdatePasswordRequest): Response<TokenResponse>
    @POST("${ConstantsServer.usersEndpoint}/friend")
    suspend fun friend(@Body dto: FriendRequest): Response<Unit>
    @GET("${ConstantsServer.usersEndpoint}/{id}/friend")
    suspend fun friends(@Path("id") id: UUID): Response<List<UserShort>>
    @GET("${ConstantsServer.usersEndpoint}/{id}/followers")
    suspend fun followers(@Path("id") id: UUID): Response<List<UserShort>>
    @GET("${ConstantsServer.usersEndpoint}/{id}/follows")
    suspend fun follows(@Path("id") id: UUID): Response<List<UserShort>>
}