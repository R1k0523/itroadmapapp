package ru.boringowl.itroadmap.data.network.api.route.client

import retrofit2.Response
import retrofit2.http.*
import ru.boringowl.itroadmap.data.network.api.route.response.SkillTodoDto
import ru.boringowl.itroadmap.data.network.base.ConstantsServer
import ru.boringowl.itroadmap.data.network.base.ListResponse
import java.util.*

interface SkillTodoApi {
    @GET("${ConstantsServer.skillTodoEndpoint}/todo/{todoId}")
    suspend fun getByTodo(@Path("todoId") todoId: UUID): Response<ListResponse<SkillTodoDto>>

    @GET("${ConstantsServer.skillTodoEndpoint}/{skillTodoId}")
    suspend fun get(@Path("skillTodoId") skillTodoId: UUID): Response<SkillTodoDto>

    @POST(ConstantsServer.skillTodoEndpoint)
    suspend fun add(@Body model: SkillTodoDto): Response<SkillTodoDto>

    @PUT(ConstantsServer.skillTodoEndpoint)
    suspend fun update(@Body model: SkillTodoDto): Response<SkillTodoDto>

    @DELETE("${ConstantsServer.skillTodoEndpoint}/{id}")
    suspend fun delete(@Path("id") id: UUID): Response<Unit>

}