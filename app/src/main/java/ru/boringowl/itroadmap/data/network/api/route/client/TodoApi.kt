package ru.boringowl.itroadmap.data.network.api.route.client

import retrofit2.Response
import retrofit2.http.*
import ru.boringowl.itroadmap.data.network.api.route.response.TodoDto
import ru.boringowl.itroadmap.data.network.base.ConstantsServer
import ru.boringowl.itroadmap.data.network.base.ListResponse
import java.util.*

interface TodoApi {
    @GET(ConstantsServer.todoEndpoint)
    suspend fun get(): Response<ListResponse<TodoDto>>

    @GET("${ConstantsServer.todoEndpoint}/{id}")
    suspend fun get(@Path("id") id: UUID): Response<TodoDto>

    @POST("${ConstantsServer.todoEndpoint}/{id}")
    suspend fun add(@Path("id") id: Int, @Query("name") name: String): Response<TodoDto>

    @PUT(ConstantsServer.todoEndpoint)
    suspend fun update(@Body model: TodoDto): Response<TodoDto>

    @DELETE("${ConstantsServer.todoEndpoint}/{id}")
    suspend fun delete(@Path("id") id: UUID): Response<Unit>

}