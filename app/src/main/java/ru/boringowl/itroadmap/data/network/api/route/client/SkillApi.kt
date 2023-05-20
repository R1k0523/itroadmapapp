package ru.boringowl.itroadmap.data.network.api.route.client

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.boringowl.itroadmap.data.network.api.route.response.SkillResponse
import ru.boringowl.itroadmap.data.network.base.ConstantsServer
import ru.boringowl.itroadmap.data.network.base.ListResponse
import java.util.*

interface SkillApi {
    @GET("${ConstantsServer.skillEndpoint}/route/{id}")
    suspend fun getByRoute(@Path("id") id: Int): Response<ListResponse<SkillResponse>>

    @GET("${ConstantsServer.skillEndpoint}/{id}")
    suspend fun get(
        @Path("id") id: UUID,
    ): Response<SkillResponse>
}