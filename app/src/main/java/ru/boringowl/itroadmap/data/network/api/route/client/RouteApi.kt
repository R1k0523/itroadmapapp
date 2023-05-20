package ru.boringowl.itroadmap.data.network.api.route.client

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.boringowl.itroadmap.data.network.api.route.response.RouteResponse
import ru.boringowl.itroadmap.data.network.base.ConstantsServer
import ru.boringowl.itroadmap.data.network.base.ListResponse
import java.util.*

interface RouteApi {
    @GET(ConstantsServer.routeEndpoint)
    suspend fun get(): Response<ListResponse<RouteResponse>>

    @GET("${ConstantsServer.routeEndpoint}/{id}")
    suspend fun get(@Path("id") id: Int): Response<RouteResponse>
}